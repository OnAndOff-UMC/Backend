package com.onnoff.onnoff.domain.on.resolution.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.GeneralException;
import com.onnoff.onnoff.apiPayload.exception.handler.ResolutionHandler;
import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.resolution.repository.ResolutionRepository;
import com.onnoff.onnoff.domain.user.User;
import com.onnoff.onnoff.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService{
    private final UserRepository userRepository;
    private final ResolutionRepository resolutionRepository;

    @Override
    public List<Resolution> getAll(Long userId, LocalDate date){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        return resolutionRepository.findAllByUserAndDate(user, date).stream().toList();
    }

    @Override
    @Transactional
    public Resolution addResolution(Long userId, LocalDate date, ResolutionRequest.AddResolutionDTO request){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Long order = resolutionRepository.countByUserAndDate(user, date);

        Resolution resolution = ResolutionConverter.toAddResolution(date, order+1, request);
        resolution.setUser(user);

        return resolutionRepository.save(resolution);
    }

    @Override
    @Transactional
    public void modifyResolution(Long userId, LocalDate date, List<ResolutionRequest.ResolutionDTO> requestList){
        for(ResolutionRequest.ResolutionDTO request : requestList){

            Resolution resolutionToModify = resolutionRepository.findById(request.getResolutionId())
                    .orElseThrow(() -> new ResolutionHandler(ErrorStatus.RESOLUTION_NOT_FOUND));
            resolutionToModify.modifyResolution(request.getOrder(), request.getContent());
        }
    }

    @Override
    @Transactional
    public void deleteResolution(Long userId, LocalDate date, Long resolutionId){
        Resolution resolutionToDelete = resolutionRepository.findById(resolutionId)
                .orElseThrow(() -> new ResolutionHandler(ErrorStatus.RESOLUTION_NOT_FOUND));

        //해당 resolution 아래 객체들 순서 당겨주기
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
        List<Resolution> resolutionList = resolutionRepository.findAllByUserAndDate(user, date).stream().toList();

        for(Resolution resolution : resolutionList){
            if(resolution.getOrder() > resolutionToDelete.getOrder()){
                resolution.setOrder(resolution.getOrder() - 1);
            }
        }


        resolutionRepository.delete(resolutionToDelete);
    }
}
