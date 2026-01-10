package com.app.controller;

import com.app.dto.ConsultationDto;
import com.app.dto.PaginatedResponse;
import com.app.service.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/consultations")
@RequiredArgsConstructor
public class ConsultationController {

    private final ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationDto> createConsultation(@RequestBody ConsultationDto consultationDto) {
        ConsultationDto created = consultationService.createConsultation(consultationDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultationDto> updateConsultation(@PathVariable Long id,
            @RequestBody ConsultationDto consultationDto) {
        ConsultationDto updated = consultationService.updateConsultation(id, consultationDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsultation(@PathVariable Long id) {
        consultationService.deleteConsultation(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ConsultationDto>> getAllConsultations(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) {
        PaginatedResponse<ConsultationDto> consultations = consultationService.getAllConsultations(page, size);
        return ResponseEntity.ok(consultations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultationDto> getConsultationById(@PathVariable Long id) {
        ConsultationDto consultation = consultationService.getConsultationById(id);
        return ResponseEntity.ok(consultation);
    }
}
