package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;

public interface WorklogService {

    Worklog addWorklog(WorklogRequest.AddWorklogDTO request);

    Worklog  modifyContent(Long worklogId, WorklogRequest.ModifyWorklogDTO request);

    Worklog  modifyDate(Long worklogId);

    Worklog  modifyIsChecked(Long worklogId);

    void deleteWorklog(Long worklogId);
}
