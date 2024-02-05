package com.onnoff.onnoff.apiPayload.exception.handler;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;

public class FeedImageHandler extends GeneralException {
    public FeedImageHandler(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
