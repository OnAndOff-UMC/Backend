package com.onnoff.onnoff.domain.weekday.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class WeekdayResponseDTO {

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekdayDTO {
        LocalDate monday;
        LocalDate tuesday;
        LocalDate wednesday;
        LocalDate thursday;
        LocalDate friday;
        LocalDate saturday;
        LocalDate sunday;
    }
}
