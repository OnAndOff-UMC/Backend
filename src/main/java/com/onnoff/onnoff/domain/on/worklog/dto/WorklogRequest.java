package com.onnoff.onnoff.domain.on.worklog.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

public class WorklogRequest {
    @Getter
    public static class AddWorklogDTO{
        @PastOrPresent
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
