package com.onnoff.onnoff.domain.on.resolution.converter;

import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionResponse;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class ResolutionConverter {
    //request to entity
    public static Resolution toAddResolution(Long order, ResolutionRequest.AddResolutionDTO request){
       return  Resolution.builder()
               .date(request.getDate())
               .order(order.intValue())
               .content(request.getContent())
               .build();
    }


    //entity to response
    public static ResolutionResponse.AddResultDTO toAddResolutionResultDTO(Resolution resolution){
        return ResolutionResponse.AddResultDTO.builder()
                .resolutionId(resolution.getId())
                .createdAt(resolution.getCreatedAt())
                .build();
    }

    public static ResolutionResponse.ResolutionViewDTO toResolutionViewDTO(Long userId, LocalDate date, List<Resolution> resolutionList){
        List<ResolutionResponse.ResolutionDTO> resolutionDTOList = resolutionList.stream()
                .map(resolution -> ResolutionResponse.ResolutionDTO.builder()
                        .resolutionId(resolution.getId())
                        .order(resolution.getOrder())
                        .content(resolution.getContent())
                        .build())
                .collect(Collectors.toList());

        return ResolutionResponse.ResolutionViewDTO.builder()
                .userId(userId)
                .date(date)
                .resolutionDTOList(resolutionDTOList)
                .build();
    }
}
