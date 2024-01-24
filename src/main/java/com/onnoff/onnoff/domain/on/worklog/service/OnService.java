package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;

import java.time.LocalDate;

public interface OnService {
    public OnResponse.OnViewDTO getOn(Long userId, LocalDate date);
}
