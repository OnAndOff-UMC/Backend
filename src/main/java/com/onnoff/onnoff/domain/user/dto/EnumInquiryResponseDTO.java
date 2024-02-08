package com.onnoff.onnoff.domain.user.dto;

import com.onnoff.onnoff.domain.user.enums.ExperienceYear;
import com.onnoff.onnoff.domain.user.enums.FieldOfWork;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumInquiryResponseDTO {

    public static class FieldOfWorkResponseDTO{
        public static List<String> getAllField() {
            return Arrays.stream(FieldOfWork.values())
                    .map(FieldOfWork::getValue)
                    .collect(Collectors.toList());
        }
    }
    public static class ExperienceYearResponseDTO{
        public static List<String> getAllExperience() {
            return Arrays.stream(ExperienceYear.values())
                    .map(ExperienceYear::getValue)
                    .collect(Collectors.toList());
        }
    }
}
