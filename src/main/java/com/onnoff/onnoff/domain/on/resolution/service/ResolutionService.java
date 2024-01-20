package com.onnoff.onnoff.domain.on.resolution.service;

import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;

import java.time.LocalDate;
import java.util.List;

public interface ResolutionService {

    List<Resolution> getAll(Long userId, LocalDate date);

    Resolution addResolution(Long userId, LocalDate date, ResolutionRequest.AddResolutionDTO request);

    void modifyResolution(Long userId, LocalDate date, List<ResolutionRequest.ResolutionDTO> requestList);

    void deleteResolution(Long userId, LocalDate date, Long resolutionId);
}
