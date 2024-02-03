package com.onnoff.onnoff.domain.on.worklog.service;

import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.resolution.repository.ResolutionRepository;
import com.onnoff.onnoff.domain.on.worklog.converter.OnConverter;
import com.onnoff.onnoff.domain.on.worklog.dto.OnResponse;
import com.onnoff.onnoff.domain.on.worklog.entity.Worklog;
import com.onnoff.onnoff.domain.on.worklog.repository.WorklogRepository;
import com.onnoff.onnoff.domain.user.User;
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
    private final ResolutionRepository resolutionRepository;
    private final WorklogRepository worklogRepository;

    @Override
    public OnResponse.OnViewDTO getOn(LocalDate date){
        LocalDate localDate = Objects.requireNonNullElseGet(date, LocalDate::now);

        User user = UserContext.getUser();

        List<Resolution> resolutionList = resolutionRepository.findAllByUserAndDateOrderByOrder(user, localDate);

        List<Worklog> worklogList = worklogRepository.findAllByUserAndDateOrderByCreatedAt(user, localDate);


        return OnConverter.getOnDTO(user.getId(), localDate, resolutionList, worklogList);
    }
}
