package com.onnoff.onnoff.domain.on.worklog.converter;

import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionResponse;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;
import com.onnoff.onnoff.domain.on.worklog.dto.WorklogResponse;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OnConverter {
    public static OnResponse.OnViewDTO getOnDTO(Long userId, LocalDate date,
                                                List<Resolution> resolutionList,
                                                List<Worklog> worklogList){
        LocalDate monday = date.minusDays(date.getDayOfWeek().getValue() - 1);

        OnResponse.WeekDTO weekDTO =OnResponse.WeekDTO.builder()
                .mon(monday)
                .tue(monday.plusDays(1))
                .wed(monday.plusDays(2))
                .thu(monday.plusDays(3))
                .fri(monday.plusDays(4))
                .sat(monday.plusDays(5))
                .sun(monday.plusDays(6))
                .build();

        List<ResolutionResponse.ResolutionDTO> resolutionDTOList = resolutionList.stream()
                .map(resolution -> ResolutionResponse.ResolutionDTO.builder()
                        .resolutionId(resolution.getId())
                        .order(resolution.getOrder())
                        .content(resolution.getContent())
                        .build())
                .collect(Collectors.toList());

        List<WorklogResponse.WorklogDTO> worklogDTOList = worklogList.stream()
                .map(worklog -> WorklogResponse.WorklogDTO.builder()
                        .worklogId(worklog.getId())
                        .content(worklog.getContent())
                        .isChecked(worklog.getIsChecked())
                        .build())
                .collect(Collectors.toList());

        return OnResponse.OnViewDTO.builder()
                .userId(userId)
                .date(date)
                .week(weekDTO)
                .resolutionDTOList(resolutionDTOList)
                .worklogDTOList(worklogDTOList)
                .build();
    }
}
