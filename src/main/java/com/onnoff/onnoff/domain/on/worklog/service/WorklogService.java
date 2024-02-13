package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface WorklogService {

    List<Worklog> getWorklog(LocalDate date);

    Worklog addWorklog(WorklogRequest.AddWorklogDTO request);

    Worklog  modifyContent(Long worklogId, WorklogRequest.ModifyWorklogDTO request);

    Worklog  modifyDate(Long worklogId);

    Worklog  modifyIsChecked(Long worklogId);

    void deleteWorklog(Long worklogId);
}
