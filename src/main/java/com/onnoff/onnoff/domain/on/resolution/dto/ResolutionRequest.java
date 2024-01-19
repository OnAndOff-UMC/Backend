package com.onnoff.onnoff.domain.on.resolution.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

public class ResolutionRequest {
    @Getter
    public static class AddResolutionDTO{
        @Size(max = 30)
        String content;
    }
    

    @Getter
    public static class ResolutionDTO{
        Long resolutionId;
        Integer order;
        String content;
    }
}
