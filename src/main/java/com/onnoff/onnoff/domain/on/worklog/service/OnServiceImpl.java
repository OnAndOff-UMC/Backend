package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.resolution.repository.ResolutionRepository;
import com.onnoff.onnoff.domain.on.worklog.converter.OnConverter;
import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.on.worklog.repository.WorklogRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class OnServiceImpl implements OnService{
    private final UserRepository userRepository;
    private final ResolutionRepository resolutionRepository;
    private final WorklogRepository worklogRepository;

    @Override
    public OnResponse.OnViewDTO getOn(Long userId, LocalDate date){
        LocalDate localDate = Objects.requireNonNullElseGet(date, LocalDate::now);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<Resolution> resolutionList = resolutionRepository.findAllByUserAndDate(user, localDate);

        List<Worklog> worklogList = worklogRepository.findAllByUserAndDate(user, localDate);


        return OnConverter.getOnDTO(userId, localDate, resolutionList, worklogList);
    }
}
