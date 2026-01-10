package com.app.service.impl;

import com.app.dto.BilingualField;
import com.app.dto.PaginatedResponse;
import com.app.dto.ResearchDto;
import com.app.entity.BilingualText;
import com.app.entity.Research;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.ResearchRepository;
import com.app.service.ResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .title(toBilingualText(researchDto.getTitle()))
                .body(toBilingualText(researchDto.getBody()))
                .shortDescription(toBilingualText(researchDto.getShortDescription()))
                .build();

        Research saved = researchRepository.save(research);
        return mapToDto(saved);
    }

    @Override
    public ResearchDto updateResearch(Long id, ResearchDto researchDto) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "researchId", id));

        if (researchDto.getResearchPhoto() != null) {
            research.setResearchPhoto(researchDto.getResearchPhoto());
        }
        if (researchDto.getTitle() != null) {
            research.setTitle(mergeBilingualText(research.getTitle(), researchDto.getTitle()));
        }
        if (researchDto.getBody() != null) {
            research.setBody(mergeBilingualText(research.getBody(), researchDto.getBody()));
        }
        if (researchDto.getShortDescription() != null) {
            research.setShortDescription(
                    mergeBilingualText(research.getShortDescription(), researchDto.getShortDescription()));
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
    public PaginatedResponse<ResearchDto> getAllResearch(int page, int size) {
        Page<Research> researchPage = researchRepository.findAll(PageRequest.of(page - 1, size));

        List<ResearchDto> researches = researchPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginatedResponse.PageMetadata metadata = PaginatedResponse.PageMetadata.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(researchPage.getTotalPages())
                .totalElements(researchPage.getTotalElements())
                .build();

        return PaginatedResponse.<ResearchDto>builder()
                .data(researches)
                .meta(metadata)
                .build();
    }

    @Override
    public ResearchDto getResearchById(Long id) {
        Research research = researchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "researchId", id));
        return mapToDto(research);
    }

    @Override
    public ResearchDto getResearchBySlug(String slug) {
        Research research = researchRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Research", "slug", slug));
        return mapToDto(research);
    }

    private ResearchDto mapToDto(Research research) {
        return ResearchDto.builder()
                .researchId(research.getResearchId())
                .slug(research.getSlug())
                .researchPhoto(research.getResearchPhoto())
                .title(toBilingualField(research.getTitle()))
                .body(toBilingualField(research.getBody()))
                .shortDescription(toBilingualField(research.getShortDescription()))
                .build();
    }

    private BilingualText toBilingualText(BilingualField field) {
        if (field == null)
            return null;
        return BilingualText.builder()
                .en(field.getEn())
                .ar(field.getAr())
                .build();
    }

    private BilingualText mergeBilingualText(BilingualText existing, BilingualField updates) {
        if (updates == null)
            return existing;
        if (existing == null)
            return toBilingualText(updates);

        return BilingualText.builder()
                .en(updates.getEn() != null ? updates.getEn() : existing.getEn())
                .ar(updates.getAr() != null ? updates.getAr() : existing.getAr())
                .build();
    }

    private BilingualField toBilingualField(BilingualText text) {
        if (text == null)
            return null;
        return BilingualField.builder()
                .en(text.getEn())
                .ar(text.getAr())
                .build();
    }
}
