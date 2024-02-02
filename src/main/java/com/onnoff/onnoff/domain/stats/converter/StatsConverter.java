package com.onnoff.onnoff.domain.stats.converter;

import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;

import java.time.LocalDate;
import java.util.List;

public class StatsConverter {
    public static StatsResponseDTO.WeekDTO getWeekStatsDTO(LocalDate today, Double on, Double off,
                                                           Integer weekOfMonth, List<Double> weekStats){
        StatsResponseDTO.WeekStatsDTO weekStatsDTO = StatsResponseDTO.WeekStatsDTO.builder()
                .mon(weekStats.get(0))
                .tue(weekStats.get(1))
                .wed(weekStats.get(2))
                .thu(weekStats.get(3))
                .fri(weekStats.get(4))
                .sat(weekStats.get(5))
                .sun(weekStats.get(6))
                .build();

        return StatsResponseDTO.WeekDTO.builder()
                .today(today)
                .on(on)
                .off(off)
                .weekOfMonth(weekOfMonth)
                .weekStatsDTO(weekStatsDTO)
                .build();
    }
}
