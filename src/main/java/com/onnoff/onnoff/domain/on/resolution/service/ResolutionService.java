package com.onnoff.onnoff.domain.on.resolution.service;

import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;

import java.time.LocalDate;
import java.util.List;

public interface ResolutionService {

    List<Resolution> getAll(Long userId, LocalDate date);

    Resolution addResolution(Long userId, ResolutionRequest.AddResolutionDTO request);

    void modifyResolution(Long userId, ResolutionRequest.ModifyResolutionDTO request);

    void deleteResolution(Long userId, LocalDate date, Long resolutionId);
}
