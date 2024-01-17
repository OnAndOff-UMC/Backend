package com.onnoff.onnoff.domain.off.memoir.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

public class MemoirAnswerRequestDTO {

    @Getter
    public static class WriteDTO {
        @NotNull
        Long questionId;
        @Size(max = 500)
        String answer;
    }
}
