package com.onnoff.onnoff.domain.on.worklog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

public class WorklogRequest {
    @Getter
    public static class AddWorklogDTO{
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        LocalDate date;
        @NotBlank
        @Size(max = 30)
        String content;
    }

    @Getter
    public static class ModifyWorklogDTO{
        @NotBlank
        @Size(max = 30)
        String content;
    }
}
