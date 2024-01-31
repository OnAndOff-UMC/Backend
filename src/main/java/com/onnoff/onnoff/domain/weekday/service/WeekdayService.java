package com.onnoff.onnoff.domain.weekday.service;

import com.onnoff.onnoff.domain.weekday.dto.WeekdayResponseDTO;

import java.time.LocalDate;

public interface WeekdayService {

    WeekdayResponseDTO.WeekdayResultDTO getWeekday(LocalDate date);
}
