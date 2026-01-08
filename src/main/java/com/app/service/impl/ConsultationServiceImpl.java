package com.app.service.impl;

import com.app.dto.ConsultationDto;
import com.app.entity.Consultation;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ConsultationRepository;
import com.app.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationRepository consultationRepository;

    @Override
    public ConsultationDto createConsultation(ConsultationDto dto) {
        Consultation consultation = Consultation.builder()
                .consultationStatus("OPEN") // Always set status to OPEN on creation
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .causeCategory(dto.getCauseCategory())
                .causeName(dto.getCauseName())
                .mobileNumber(dto.getMobileNumber())
                .email(dto.getEmail())
                .consultationBody(dto.getConsultationBody())
                .build();

        Consultation saved = consultationRepository.save(consultation);
        return mapToDto(saved);
    }

    @Override
    public ConsultationDto updateConsultation(Long id, ConsultationDto dto) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultationId", id));

        // Only update fields that are not null (partial update support)
        if (dto.getConsultationStatus() != null) {
            consultation.setConsultationStatus(dto.getConsultationStatus());
        }
        if (dto.getFirstName() != null) {
            consultation.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            consultation.setLastName(dto.getLastName());
        }
        if (dto.getCauseCategory() != null) {
            consultation.setCauseCategory(dto.getCauseCategory());
        }
        if (dto.getCauseName() != null) {
            consultation.setCauseName(dto.getCauseName());
        }
        if (dto.getMobileNumber() != null) {
            consultation.setMobileNumber(dto.getMobileNumber());
        }
        if (dto.getEmail() != null) {
            consultation.setEmail(dto.getEmail());
        }
        if (dto.getConsultationBody() != null) {
            consultation.setConsultationBody(dto.getConsultationBody());
        }

        Consultation updated = consultationRepository.save(consultation);
        return mapToDto(updated);
    }

    @Override
    public void deleteConsultation(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultationId", id));
        consultationRepository.delete(consultation);
    }

    @Override
    public List<ConsultationDto> getAllConsultations() {
        return consultationRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ConsultationDto getConsultationById(Long id) {
        Consultation consultation = consultationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consultation", "consultationId", id));
        return mapToDto(consultation);
    }

    private ConsultationDto mapToDto(Consultation consultation) {
        return ConsultationDto.builder()
                .consultationId(consultation.getConsultationId())
                .consultationStatus(consultation.getConsultationStatus())
                .firstName(consultation.getFirstName())
                .lastName(consultation.getLastName())
                .causeCategory(consultation.getCauseCategory())
                .causeName(consultation.getCauseName())
                .mobileNumber(consultation.getMobileNumber())
                .email(consultation.getEmail())
                .consultationBody(consultation.getConsultationBody())
                .build();
    }
}
