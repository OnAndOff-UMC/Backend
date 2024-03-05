package com.onnoff.onnoff.domain.stats.service;

import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.off.feed.repository.FeedRepository;
import com.onnoff.onnoff.domain.off.memoir.entity.Memoir;
import com.onnoff.onnoff.domain.off.memoir.entity.MemoirAnswer;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirAnswerRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirRepository;
import com.onnoff.onnoff.domain.on.worklog.repository.WorklogRepository;
import com.onnoff.onnoff.domain.stats.converter.StatsConverter;
import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService{
    private final WorklogRepository worklogRepository;
    private final FeedRepository feedRepository;
    private final MemoirRepository memoirRepository;
    private final MemoirAnswerRepository memoirAnswerRepository;
    private final UserService userService;

    @Override
    public StatsResponseDTO.WeekDTO getWeekStats(){
        LocalDate today = LocalDate.now();

        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);

        Double on = worklogRepository.countByUserAndDateAndIsChecked(user, today, true) / worklogRepository.countByUserAndDate(user, today).doubleValue();
        if(on.isNaN()) {
            on = 0.0;
        }
        Double off = feedRepository.countByUserAndDateAndIsChecked(user, today, true) / feedRepository.countByUserAndDate(user, today).doubleValue();
        if(off.isNaN()) {
            off = 0.0;
        }

        WeekFields weekFields = WeekFields.of(DayOfWeek.MONDAY, 1);
        Integer weekOfMonth = today.get(weekFields.weekOfMonth());

        List<Double> weekStats = new ArrayList<>();
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1);
        for (int i=0; i<7; i++){
            if(i > today.getDayOfWeek().getValue() - 1){
                weekStats.add(0.0);
                continue;
            }
            LocalDate date = monday.plusDays(i);
            Double offW = feedRepository.countByUserAndDateAndIsChecked(user, date, true) / feedRepository.countByUserAndDate(user, date).doubleValue();
            if(offW.isNaN()){
                offW = 0.0;
            }

            weekStats.add(offW);
        }

        return StatsConverter.getWeekStatsDTO(today, on, off, weekOfMonth, weekStats);
    }

    @Override
    public StatsResponseDTO.MonthDTO getMonthStats(LocalDate date){
        if(date == null){
            date = LocalDate.now();
        }

        Double avg = 0.0;

        Long userId = UserContext.getUserId();
        User user = userService.getUser(userId);

        List<StatsResponseDTO.MonthStatsDTO> monthStatsList = new ArrayList<>();
        LocalDate localDate = date.minusDays(date.getDayOfMonth() - 1);
        for(int i=0; i<date.lengthOfMonth(); i++){
            double rate =0.0;

            Memoir memoir = memoirRepository.findByUserAndDate(user, localDate.plusDays(i)).orElse(null);
            if(memoir == null){
                StatsResponseDTO.MonthStatsDTO stats = StatsResponseDTO.MonthStatsDTO.builder()
                        .date(localDate.plusDays(i))
                        .rate(0.0)
                        .build();
                monthStatsList.add(stats);
                continue;
            }

            if(memoir.getEmoticon() != null){
                rate++;
            }

            List<MemoirAnswer> memoirAnswerList = memoirAnswerRepository.findAllByMemoirId(memoir.getId()).stream().toList();
            for(MemoirAnswer memoirAnswer: memoirAnswerList){
                if(!memoirAnswer.getAnswer().equals("")){
                    rate++;
                }
            }

            StatsResponseDTO.MonthStatsDTO stats = StatsResponseDTO.MonthStatsDTO.builder()
                    .date(localDate.plusDays(i))
                    .rate(rate / (memoirAnswerList.size() + 1))
                    .build();
            monthStatsList.add(stats);

            avg += rate / 4.0 * 100;
        }

        if(date.equals(LocalDate.now())){
            //이번달은 오늘 기준으로 계산
            avg = avg / date.getDayOfMonth();
        }
        else{
            //이외에는 한 달 기준으로 계산
            avg = avg / date.lengthOfMonth();
        }

        return StatsConverter.getMonthStatsDTO(date, Integer.parseInt(String.valueOf(Math.round(avg))), monthStatsList);
    }
}
