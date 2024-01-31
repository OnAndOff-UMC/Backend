package com.onnoff.onnoff.domain.stats.service;

import com.onnoff.onnoff.domain.stats.dto.StatsResponseDTO;

public interface StatsService {
    public StatsResponseDTO.WeekDTO getWeekStats(Long userId);
}
