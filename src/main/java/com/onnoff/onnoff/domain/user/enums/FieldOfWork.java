package com.onnoff.onnoff.domain.user.enums;


import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FieldOfWork {

    서비스업("서비스업"),
    의료_제약_복지("의료•제약•복지"),
    제조_화학("제조•화학"),
    판매_유통("판매•유통"),
    IT_웹_통신("IT•웹•통신"),
    건설업("건설업"),
    교육업("교육업"),
    미디어_디자인("미디어•디자인"),
    은행_금융업("은행•금융업"),
    기관_협회("기관•협회"),
    비즈니스_투자("비즈니스•투자"),
    물류_무역업("물류•무역업"),
    법률_법집행기관("법률•법집행기관"),
    방송_광고_엔터테인먼트("방송•광고•엔터테인먼트"),
    여행_숙박_음식점업("여행•숙박•음식점업"),
    부동산_임대업("부동산•임대업");

    private final String value;

    public static FieldOfWork fromValue(String value) {
        for (FieldOfWork fieldOfWork : FieldOfWork.values()) {
            if (fieldOfWork.getValue().equals(value)) {
                return fieldOfWork;
            }
        }
        throw new GeneralException(ErrorStatus.INVALID_ENUM_VALUE);
    }
}
