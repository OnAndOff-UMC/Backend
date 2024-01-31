package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;

import java.time.LocalDate;
import java.util.List;

public interface WorklogService {

    Worklog addWorklog(Long userId, WorklogRequest.AddWorklogDTO request);

    Worklog  modifyContent(Long worklogId, WorklogRequest.ModifyWorklogDTO request);

    Worklog  modifyDate(Long worklogId);

    Worklog  modifyIsChecked(Long worklogId);

    void deleteWorklog(Long worklogId);
}
