package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;

import java.time.LocalDate;

public interface OnService {
    OnResponse.OnViewDTO getOn(LocalDate date);
}
