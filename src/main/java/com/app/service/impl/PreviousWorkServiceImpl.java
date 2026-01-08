package com.app.service.impl;

import com.app.dto.PreviousWorkDto;
import com.app.entity.PreviousWork;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.PreviousWorkRepository;
import com.app.service.PreviousWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreviousWorkServiceImpl implements PreviousWorkService {

    private final PreviousWorkRepository previousWorkRepository;

    @Override
    public PreviousWorkDto createPreviousWork(PreviousWorkDto dto) {
        PreviousWork previousWork = PreviousWork.builder()
                .englishPreviousWorkName(dto.getEnglishPreviousWorkName())
                .englishSummary(dto.getEnglishSummary())
                .englishCaseName(dto.getEnglishCaseName())
                .englishCaseCategory(dto.getEnglishCaseCategory())
                .arabicPreviousWorkName(dto.getArabicPreviousWorkName())
                .arabicSummary(dto.getArabicSummary())
                .arabicCaseName(dto.getArabicCaseName())
                .arabicCaseCategory(dto.getArabicCaseCategory())
                .caseFile(dto.getCaseFile())
                .build();

        PreviousWork saved = previousWorkRepository.save(previousWork);
        return mapToDto(saved);
    }

    @Override
    public PreviousWorkDto updatePreviousWork(Long id, PreviousWorkDto dto) {
        PreviousWork previousWork = previousWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "previousWorkId", id));

        // Only update fields that are not null (partial update support)
        if (dto.getEnglishPreviousWorkName() != null) {
            previousWork.setEnglishPreviousWorkName(dto.getEnglishPreviousWorkName());
        }
        if (dto.getEnglishSummary() != null) {
            previousWork.setEnglishSummary(dto.getEnglishSummary());
        }
        if (dto.getEnglishCaseName() != null) {
            previousWork.setEnglishCaseName(dto.getEnglishCaseName());
        }
        if (dto.getEnglishCaseCategory() != null) {
            previousWork.setEnglishCaseCategory(dto.getEnglishCaseCategory());
        }
        if (dto.getArabicPreviousWorkName() != null) {
            previousWork.setArabicPreviousWorkName(dto.getArabicPreviousWorkName());
        }
        if (dto.getArabicSummary() != null) {
            previousWork.setArabicSummary(dto.getArabicSummary());
        }
        if (dto.getArabicCaseName() != null) {
            previousWork.setArabicCaseName(dto.getArabicCaseName());
        }
        if (dto.getArabicCaseCategory() != null) {
            previousWork.setArabicCaseCategory(dto.getArabicCaseCategory());
        }
        if (dto.getCaseFile() != null) {
            previousWork.setCaseFile(dto.getCaseFile());
        }

        PreviousWork updated = previousWorkRepository.save(previousWork);
        return mapToDto(updated);
    }

    @Override
    public void deletePreviousWork(Long id) {
        PreviousWork previousWork = previousWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "previousWorkId", id));
        previousWorkRepository.delete(previousWork);
    }

    @Override
    public List<PreviousWorkDto> getAllPreviousWorks() {
        return previousWorkRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public PreviousWorkDto getPreviousWorkById(Long id) {
        PreviousWork previousWork = previousWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "previousWorkId", id));
        return mapToDto(previousWork);
    }

    private PreviousWorkDto mapToDto(PreviousWork previousWork) {
        return PreviousWorkDto.builder()
                .previousWorkId(previousWork.getPreviousWorkId())
                .englishPreviousWorkName(previousWork.getEnglishPreviousWorkName())
                .englishSummary(previousWork.getEnglishSummary())
                .englishCaseName(previousWork.getEnglishCaseName())
                .englishCaseCategory(previousWork.getEnglishCaseCategory())
                .arabicPreviousWorkName(previousWork.getArabicPreviousWorkName())
                .arabicSummary(previousWork.getArabicSummary())
                .arabicCaseName(previousWork.getArabicCaseName())
                .arabicCaseCategory(previousWork.getArabicCaseCategory())
                .caseFile(previousWork.getCaseFile())
                .build();
    }
}
