package com.onnoff.onnoff.domain.stats.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class StatsResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekStatsDTO{
        Double mon;
        Double tue;
        Double wed;
        Double thu;
        Double fri;
        Double sat;
        Double sun;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekDTO{
        LocalDate today;
        Double on;
        Double off;
        Integer weekOfMonth;
        WeekStatsDTO weekStatsDTO;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthStatsDTO{
        LocalDate date;
        Double rate;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MonthDTO{
        LocalDate date;
        Integer avg;
        List<MonthStatsDTO> monthStatsList;
    }
}
