package com.onnoff.onnoff.apiPayload.exception.handler;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;

public class MemoirHandler extends GeneralException {
    public MemoirHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
