package com.onnoff.onnoff.domain.on.worklog.dto;

import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

public class OnResponse {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class WeekDTO{
        LocalDate mon;
        LocalDate tue;
        LocalDate wed;
        LocalDate thu;
        LocalDate fri;
        LocalDate sat;
        LocalDate sun;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OnViewDTO{
        Long userId;
        LocalDate date;
        WeekDTO week;
        List<ResolutionResponse.ResolutionDTO> resolutionDTOList;
        List<WorklogResponse.WorklogDTO> worklogDTOList;
    }
}
