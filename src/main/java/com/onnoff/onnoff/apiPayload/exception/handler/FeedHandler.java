package com.onnoff.onnoff.apiPayload.exception.handler;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;

public class FeedHandler extends GeneralException {
    public FeedHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
