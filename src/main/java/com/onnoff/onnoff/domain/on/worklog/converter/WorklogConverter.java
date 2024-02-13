package com.onnoff.onnoff.domain.on.worklog.converter;

import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.dto.WorklogResponse;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;

import java.util.List;
import java.util.stream.Collectors;

public class WorklogConverter {
    //request to entity
    public static Worklog toAddWorklog(WorklogRequest.AddWorklogDTO request) {
        return Worklog.builder()
                .date(request.getDate())
                .content(request.getContent())
                .isChecked(false)
                .build();
    }


    //entity to response
    public static List<WorklogResponse.WorklogDTO> toWorklogViewDTO(List<Worklog> worklogList) {
        return worklogList.stream()
                .map(worklog -> WorklogResponse.WorklogDTO.builder()
                        .worklogId(worklog.getId())
                        .date(worklog.getDate())
                        .content(worklog.getContent())
                        .isChecked(worklog.getIsChecked())
                        .build())
                .collect(Collectors.toList());
    }

    public static WorklogResponse.AddResultDTO toAddWorklogResultDTO(Worklog worklog) {
        return WorklogResponse.AddResultDTO.builder()
                .worklogId(worklog.getId())
                .date(worklog.getDate())
                .content(worklog.getContent())
                .isChecked(worklog.getIsChecked())
                .createdAt(worklog.getCreatedAt())
                .build();
    }

    public static WorklogResponse.ModifyResultDTO toModifyWorklogResultDTO(Worklog worklog) {
        return WorklogResponse.ModifyResultDTO.builder()
                .worklogId(worklog.getId())
                .content(worklog.getContent())
                .date(worklog.getDate())
                .isChecked(worklog.getIsChecked())
                .updatedAt(worklog.getUpdatedAt())
                .build();
    }

}
