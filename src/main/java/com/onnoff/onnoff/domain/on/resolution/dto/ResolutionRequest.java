package com.onnoff.onnoff.domain.on.resolution.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class ResolutionRequest {
    @Getter
    public static class AddResolutionDTO{
        @NotBlank
        @Size(max = 30)
        String content;
    }
    

    @Getter
    public static class ResolutionDTO{
        @NotNull
        Long resolutionId;
        @NotNull
        @Min(value = 1)
        Integer order;
        @NotBlank
        @Size(max = 30, message = "30자 이하로 작성해주세요.")
        String content;
    }
}
