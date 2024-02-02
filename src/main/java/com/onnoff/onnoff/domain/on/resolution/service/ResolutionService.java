package com.onnoff.onnoff.domain.on.resolution.service;

import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;

import java.time.LocalDate;
import java.util.List;

public interface ResolutionService {

    List<Resolution> getAll(LocalDate date);

    Resolution addResolution(ResolutionRequest.AddResolutionDTO request);

    List<Resolution> modifyResolution(ResolutionRequest.ModifyResolutionDTO request);

    void deleteResolution(LocalDate date, Long resolutionId);
}
