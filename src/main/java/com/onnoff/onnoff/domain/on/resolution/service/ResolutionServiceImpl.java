package com.onnoff.onnoff.domain.on.resolution.service;

import com.onnoff.onnoff.apiPayload.code.status.ErrorStatus;
import com.onnoff.onnoff.apiPayload.exception.handler.ResolutionHandler;
import com.onnoff.onnoff.auth.UserContext;
import com.onnoff.onnoff.domain.on.resolution.converter.ResolutionConverter;
import com.onnoff.onnoff.domain.on.resolution.dto.ResolutionRequest;
import com.onnoff.onnoff.domain.on.resolution.entity.Resolution;
import com.onnoff.onnoff.domain.on.resolution.repository.ResolutionRepository;
import com.onnoff.onnoff.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ResolutionServiceImpl implements ResolutionService{
    private final ResolutionRepository resolutionRepository;

    @Override
    public List<Resolution> getAll(LocalDate date){
        User user = UserContext.getUser();

        return resolutionRepository.findAllByUserAndDateOrderByOrder(user, date).stream().toList();
    }

    @Override
    @Transactional
    public Resolution addResolution(ResolutionRequest.AddResolutionDTO request){
        User user = UserContext.getUser();

        Long order = resolutionRepository.countByUserAndDate(user, request.getDate());

        Resolution resolution = ResolutionConverter.toAddResolution(order+1, request);
        resolution.setUser(user);

        return resolutionRepository.save(resolution);
    }

    @Override
    @Transactional
    public List<Resolution> modifyResolution(ResolutionRequest.ModifyResolutionDTO requestList){
        List<Resolution> modifiedResolutionList = new ArrayList<>();
        for(ResolutionRequest.ResolutionDTO request : requestList.getResolutionDTOList()){

            Resolution resolutionToModify = resolutionRepository.findById(request.getResolutionId())
                    .orElseThrow(() -> new ResolutionHandler(ErrorStatus.RESOLUTION_NOT_FOUND));
            resolutionToModify.modifyResolution(request.getOrder(), request.getContent());
            modifiedResolutionList.add(resolutionToModify);
        }
        return modifiedResolutionList;
    }

    @Override
    @Transactional
    public void deleteResolution(LocalDate date, Long resolutionId){
        Resolution resolutionToDelete = resolutionRepository.findById(resolutionId)
                .orElseThrow(() -> new ResolutionHandler(ErrorStatus.RESOLUTION_NOT_FOUND));

        //해당 resolution 아래 객체들 순서 당겨주기
        User user = UserContext.getUser();
        List<Resolution> resolutionList = resolutionRepository.findAllByUserAndDateOrderByOrder(user, date).stream().toList();

        for(Resolution resolution : resolutionList){
            if(resolution.getOrder() > resolutionToDelete.getOrder()){
                resolution.setOrder(resolution.getOrder() - 1);
            }
        }

        resolutionRepository.delete(resolutionToDelete);
    }
}
