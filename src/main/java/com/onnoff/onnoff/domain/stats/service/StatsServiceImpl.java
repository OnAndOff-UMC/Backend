package com.onnoff.onnoff.domain.stats.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.off.feed.repository.FeedRepository;
import com.onnoff.onnoff.domain.off.memoir.repository.MemoirRepository;
import com.onnoff.onnoff.domain.on.worklog.repository.WorklogRepository;
import com.onnoff.onnoff.domain.stats.converter.StatsConverter;
import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
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
    private final UserRepository userRepository;
    private final WorklogRepository worklogRepository;
    private final FeedRepository feedRepository;
    //private final MemoirRepository memoirRepository;

    @Override
    public StatsResponseDTO.WeekDTO getWeekStats(Long userId){
        LocalDate today = LocalDate.now();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        //User user = UserContext.getUser();

        Double on = worklogRepository.countByUserAndDateAndIsChecked(user, today, true) / worklogRepository.countByUserAndDate(user, today).doubleValue();
        Double off = feedRepository.countByUserAndDateAndIsChecked(user, today, true) / feedRepository.countByUserAndDate(user, today).doubleValue();

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

            weekStats.add(offW);
        }

        return StatsConverter.getWeekStatsDTO(today, on, off, weekOfMonth, weekStats);
    }
}
