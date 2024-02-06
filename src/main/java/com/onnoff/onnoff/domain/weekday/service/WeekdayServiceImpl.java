package com.onnoff.onnoff.domain.weekday.service;

import com.onnoff.onnoff.domain.weekday.dto.WeekdayResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class WeekdayServiceImpl implements WeekdayService {

    @Override
    public WeekdayResponseDTO.WeekdayDTO getWeekday(LocalDate date) {
        LocalDate monday = date.minusDays(date.getDayOfWeek().getValue() - 1);

        return WeekdayResponseDTO.WeekdayDTO.builder()
                .monday(monday)
                .tuesday(monday.plusDays(1))
                .wednesday(monday.plusDays(2))
                .thursday(monday.plusDays(3))
                .friday(monday.plusDays(4))
                .saturday(monday.plusDays(5))
                .sunday(monday.plusDays(6))
                .build();
    }
}
