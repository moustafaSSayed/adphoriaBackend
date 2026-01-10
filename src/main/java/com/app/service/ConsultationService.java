package com.app.service;

import com.app.dto.ConsultationDto;
import com.app.dto.PaginatedResponse;

public interface ConsultationService {

    ConsultationDto createConsultation(ConsultationDto consultationDto);

    ConsultationDto updateConsultation(Long id, ConsultationDto consultationDto);

    void deleteConsultation(Long id);

    PaginatedResponse<ConsultationDto> getAllConsultations(int page, int size);

    ConsultationDto getConsultationById(Long id);
}
