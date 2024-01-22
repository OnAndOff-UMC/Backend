package com.onnoff.onnoff.apiPayload.exception.handler;

import com.onnoff.onnoff.apiPayload.code.BaseErrorCode;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;

public class ResolutionHandler extends GeneralException {
    public ResolutionHandler(BaseErrorCode errorCode){
        super(errorCode);
    }
}
