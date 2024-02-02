package com.onnoff.onnoff.domain.on.resolution.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

public class ResolutionRequest {
    @Getter
    public static class AddResolutionDTO{
        @PastOrPresent
        LocalDate date;
        @NotBlank
        @Size(max = 30)
        String content;
    }

    @Getter
    public static class ModifyResolutionDTO{
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date;
        @Valid
        List<ResolutionDTO> resolutionDTOList;
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
