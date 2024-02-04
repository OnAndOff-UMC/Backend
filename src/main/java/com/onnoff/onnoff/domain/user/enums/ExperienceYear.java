package com.onnoff.onnoff.domain.user.enums;


import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExperienceYear {

    THE_NEW("신입"),
    THE_FIRST_YEAR("1년 이상 2년 미만"),
    THE_SECOND_YEAR("2년 이상 3년 미만"),
    THE_THIRD_YEAR("3년 이상 4년 미만"),
    THE_FOURTH_YEAR("4년 이상 5년 미만"),
    THE_FIFTH_YEAR_OR_MORE("5년 이상"),
    THE_SENIOR("시니어");
    private final String value;

    public static ExperienceYear fromValue(String value) {
        for (ExperienceYear experienceYear : ExperienceYear.values()) {
            if (experienceYear.getValue().equals(value)) {
                return experienceYear;
            }
        }
        throw new GeneralException(ErrorStatus.INVALID_ENUM_VALUE);
    }
}