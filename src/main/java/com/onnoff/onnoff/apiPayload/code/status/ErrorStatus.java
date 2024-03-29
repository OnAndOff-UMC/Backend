package com.onnoff.onnoff.apiPayload.code.status;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {
    // 가장 일반적인 응답
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 멤버 관련 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "USER4001", "사용자가 없습니다."),
    NICKNAME_EXIST(HttpStatus.BAD_REQUEST, "USER4002", "이미 존재하는 닉네임입니다."),

    // 오늘의 다짐 관련 에러
    RESOLUTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "RESOLUTION4001", "해당하는 오늘의 다짐이 없습니다."),

    //업무일지 관련 에러
    WORKLOG_NOT_FOUND(HttpStatus.BAD_REQUEST, "WORKLOG4001", "해당하는 업무일지가 없습니다."),

    // 회고 질문 관련 에러
    QUESTION_NOT_FOUND(HttpStatus.BAD_REQUEST, "QUESTION4001", "해당하는 회고 질문이 없습니다."),

    // 회고 관련 에러
    MEMOIR_EXIST(HttpStatus.BAD_REQUEST, "MEMOIR4001", "이미 해당 날짜에 작성된 회고가 있습니다."),
    MEMOIR_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMOIR4002", "해당하는 회고가 없습니다."),

    // 회고 답변 관련 에러
    ANSWER_NOT_FOUND(HttpStatus.BAD_REQUEST, "ANSWER4001", "해당하는 회고 답변이 없습니다."),
    ANSWER_EXIST(HttpStatus.BAD_REQUEST, "ANSWER4002", "이미 해당 질문으로 작성된 회고 답변이 있습니다."),

    // 이모티콘 관련 에러
    EMOTICON_NOT_FOUND(HttpStatus.BAD_REQUEST, "EMOTICON4001", "해당하는 이모티콘이 없습니다."),

    // 피드 관련 에러
    FEED_NOT_FOUND(HttpStatus.BAD_REQUEST, "FEED4001", "해당하는 워라벨 피드가 없습니다."),

    // 피드 사진 관련 에러
    FEED_IMAGE_NOT_FOUND(HttpStatus.BAD_REQUEST, "FEEDIMAGE4001", "해당하는 워라벨 피드 사진이 없습니다."),
    FEED_IMAGE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "FEEDIMAGE4002", "잘못된 형식의 파일입니다."),

    // 로그인 관련 에러
    INVALID_TOKEN_ERROR(HttpStatus.UNAUTHORIZED, "LOGIN4001", "토큰값이 잘못되었거나 유효하지 않습니다."),
    INVALID_ARGUMENT_ERROR(HttpStatus.BAD_REQUEST, "LOGIN4002", "필수인자가 포함되지 않거나 인자값의 데이터 타입 또는 범위가 적절하지 않습니다."),
    INVALID_ENUM_VALUE(HttpStatus.BAD_REQUEST, "LOGIN4003", "추가로 입력한 업무분야나 연차의 형식이 올바르지 않습니다."),
    TEMPORARY_INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR , "LOGIN5000", "카카오 플랫폼 서비스의 일시적 내부 장애입니다. 잠시후에 다시 시도해주세요");


    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason(){
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus(){
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
