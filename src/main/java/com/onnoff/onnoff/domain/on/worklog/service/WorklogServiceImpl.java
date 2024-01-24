package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.apiPayload.exception.handler.WorklogHandler;
import com.onnoff.onnoff.domain.on.worklog.converter.WorklogConverter;
import com.onnoff.onnoff.domain.on.worklog.dto.WorklogRequest;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.on.worklog.repository.WorklogRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class WorklogServiceImpl implements WorklogService{
    private final UserRepository userRepository;
    private final WorklogRepository worklogRepository;

    @Override
    @Transactional
    public Worklog addWorklog(Long userId, WorklogRequest.AddWorklogDTO request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Worklog worklog = WorklogConverter.toAddWorklog(request);
        worklog.setUser(user);

        return worklogRepository.save(worklog);
    }

    @Override
    @Transactional
    public Worklog  modifyContent(Long worklogId, WorklogRequest.ModifyWorklogDTO request){
        Worklog worklogToModify = worklogRepository.findById(worklogId)
                .orElseThrow(() -> new WorklogHandler(ErrorStatus.WORKLOG_NOT_FOUND));
        worklogToModify.setContent(request.getContent());
        return worklogToModify;
    }

    @Override
    @Transactional
    public Worklog  modifyDate(Long worklogId){
        Worklog worklogToModify = worklogRepository.findById(worklogId)
                .orElseThrow(() -> new WorklogHandler(ErrorStatus.WORKLOG_NOT_FOUND));
        worklogToModify.setDate(worklogToModify.getDate().plusDays(1));
        return worklogToModify;
    }

    @Override
    @Transactional
    public Worklog  modifyIsChecked(Long worklogId){
        Worklog worklogToModify = worklogRepository.findById(worklogId)
                .orElseThrow(() -> new WorklogHandler(ErrorStatus.WORKLOG_NOT_FOUND));
        worklogToModify.setIsChecked();
        return worklogToModify;
    }

    @Override
    @Transactional
    public void deleteWorklog(Long worklogId){
        Worklog worklogToDelete = worklogRepository.findById(worklogId)
                .orElseThrow(() -> new WorklogHandler(ErrorStatus.WORKLOG_NOT_FOUND));

        worklogRepository.delete(worklogToDelete);
    }
}
