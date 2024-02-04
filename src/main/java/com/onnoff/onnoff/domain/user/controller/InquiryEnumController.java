package com.onnoff.onnoff.domain.user.controller;

import com.onnoff.onnoff.apiPayload.ApiResponse;
import com.onnoff.onnoff.domain.user.dto.EnumInquiryResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/enums")
public class InquiryEnumController {
    @GetMapping("/field-of-works")
    public ApiResponse<List<String>> getAllFieldValues(){
        return ApiResponse.onSuccess(EnumInquiryResponseDTO.FieldOfWorkResponseDTO.getAllField());
    }
    @GetMapping("/experience-years")
    public ApiResponse<List<String>> getAllExperienceValues(){
        return ApiResponse.onSuccess(EnumInquiryResponseDTO.ExperienceYearResponseDTO.getAllExperience());
    }
}
