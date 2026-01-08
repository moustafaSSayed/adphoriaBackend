package com.app.service;

import com.app.dto.ConsultationDto;
import java.util.List;

public interface ConsultationService {

    ConsultationDto createConsultation(ConsultationDto consultationDto);

    ConsultationDto updateConsultation(Long id, ConsultationDto consultationDto);

    void deleteConsultation(Long id);

    List<ConsultationDto> getAllConsultations();

    ConsultationDto getConsultationById(Long id);
}
