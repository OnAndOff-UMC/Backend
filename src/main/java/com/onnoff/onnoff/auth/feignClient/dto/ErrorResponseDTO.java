package com.onnoff.onnoff.auth.feignClient.dto;


import lombok.Getter;

@Getter
public class ErrorResponseDTO {
    private String msg;
    private int code;
}
