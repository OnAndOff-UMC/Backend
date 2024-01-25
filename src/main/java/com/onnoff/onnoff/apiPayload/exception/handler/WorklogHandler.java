package com.onnoff.onnoff.apiPayload.exception.handler;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;

public class WorklogHandler extends GeneralException {
    public WorklogHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
