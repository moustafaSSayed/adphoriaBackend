package com.app.service.impl;

import com.app.dto.ResearchDto;
import com.app.entity.Research;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ResearchRepository;
import com.app.service.ResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResearchServiceImpl implements ResearchService {

    private final ResearchRepository researchRepository;

    @Override
    public ResearchDto createResearch(ResearchDto researchDto) {
        Research research = Research.builder()
                .researchPhoto(researchDto.getResearchPhoto())
                .researchEnglishTitle(researchDto.getResearchEnglishTitle())
                .researchEnglishBody(researchDto.getResearchEnglishBody())
                .englishShortDescription(researchDto.getEnglishShortDescription())
                .researchArabicTitle(researchDto.getResearchArabicTitle())
                .researchArabicBody(researchDto.getResearchArabicBody())
                .arabicShortDescription(researchDto.getArabicShortDescription())
                .build();

        Research saved = researchRepository.save(research);
        return mapToDto(saved);
    }

    @Override
    public ResearchDto updateResearch(Long id, ResearchDto researchDto) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "researchId", id));

        // Only update fields that are not null (partial update support)
        if (researchDto.getResearchPhoto() != null) {
            research.setResearchPhoto(researchDto.getResearchPhoto());
        }
        if (researchDto.getResearchEnglishTitle() != null) {
            research.setResearchEnglishTitle(researchDto.getResearchEnglishTitle());
        }
        if (researchDto.getResearchEnglishBody() != null) {
            research.setResearchEnglishBody(researchDto.getResearchEnglishBody());
        }
        if (researchDto.getEnglishShortDescription() != null) {
            research.setEnglishShortDescription(researchDto.getEnglishShortDescription());
        }
        if (researchDto.getResearchArabicTitle() != null) {
            research.setResearchArabicTitle(researchDto.getResearchArabicTitle());
        }
        if (researchDto.getResearchArabicBody() != null) {
            research.setResearchArabicBody(researchDto.getResearchArabicBody());
        }
        if (researchDto.getArabicShortDescription() != null) {
            research.setArabicShortDescription(researchDto.getArabicShortDescription());
        }

        Research updated = researchRepository.save(research);
        return mapToDto(updated);
    }

    @Override
    public void deleteResearch(Long id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "researchId", id));
        researchRepository.delete(research);
    }

    @Override
    public List<ResearchDto> getAllResearches() {
        return researchRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ResearchDto getResearchById(Long id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "researchId", id));
        return mapToDto(research);
    }

    private ResearchDto mapToDto(Research research) {
        return ResearchDto.builder()
                .researchId(research.getResearchId())
                .researchPhoto(research.getResearchPhoto())
                .researchEnglishTitle(research.getResearchEnglishTitle())
                .researchEnglishBody(research.getResearchEnglishBody())
                .englishShortDescription(research.getEnglishShortDescription())
                .researchArabicTitle(research.getResearchArabicTitle())
                .researchArabicBody(research.getResearchArabicBody())
                .arabicShortDescription(research.getArabicShortDescription())
                .build();
    }
}
