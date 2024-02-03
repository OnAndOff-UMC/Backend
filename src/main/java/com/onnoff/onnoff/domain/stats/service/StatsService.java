package com.onnoff.onnoff.domain.stats.service;

import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;

import java.time.LocalDate;

public interface StatsService {
    StatsResponseDTO.WeekDTO getWeekStats();
    StatsResponseDTO.MonthDTO getMonthStats(LocalDate date);
}
