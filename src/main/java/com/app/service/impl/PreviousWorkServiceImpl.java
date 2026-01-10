package com.app.service.impl;

import com.app.dto.BilingualField;
import com.app.dto.PaginatedResponse;
import com.app.dto.PreviousWorkDto;
import com.app.entity.BilingualText;
import com.app.entity.PreviousWork;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.PreviousWorkRepository;
import com.app.service.PreviousWorkService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PreviousWorkServiceImpl implements PreviousWorkService {

    private final PreviousWorkRepository previousWorkRepository;

    @Override
    public PreviousWorkDto createPreviousWork(PreviousWorkDto previousWorkDto) {
        PreviousWork previousWork = PreviousWork.builder()
                .name(toBilingualText(previousWorkDto.getName()))
                .summary(toBilingualText(previousWorkDto.getSummary()))
                .caseName(toBilingualText(previousWorkDto.getCaseName()))
                .caseCategory(toBilingualText(previousWorkDto.getCaseCategory()))
                .caseFile(previousWorkDto.getCaseFile())
                .build();

        PreviousWork saved = previousWorkRepository.save(previousWork);
        return mapToDto(saved);
    }

    @Override
    public PreviousWorkDto updatePreviousWork(Long id, PreviousWorkDto previousWorkDto) {
        PreviousWork previousWork = previousWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "previousWorkId", id));

        if (previousWorkDto.getName() != null) {
            previousWork.setName(mergeBilingualText(previousWork.getName(), previousWorkDto.getName()));
        }
        if (previousWorkDto.getSummary() != null) {
            previousWork.setSummary(mergeBilingualText(previousWork.getSummary(), previousWorkDto.getSummary()));
        }
        if (previousWorkDto.getCaseName() != null) {
            previousWork.setCaseName(mergeBilingualText(previousWork.getCaseName(), previousWorkDto.getCaseName()));
        }
        if (previousWorkDto.getCaseCategory() != null) {
            previousWork.setCaseCategory(
                    mergeBilingualText(previousWork.getCaseCategory(), previousWorkDto.getCaseCategory()));
        }
        if (previousWorkDto.getCaseFile() != null) {
            previousWork.setCaseFile(previousWorkDto.getCaseFile());
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
    public PaginatedResponse<PreviousWorkDto> getAllPreviousWorks(int page, int size) {
        Page<PreviousWork> previousWorkPage = previousWorkRepository.findAll(PageRequest.of(page - 1, size));

        List<PreviousWorkDto> previousWorks = previousWorkPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginatedResponse.PageMetadata metadata = PaginatedResponse.PageMetadata.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(previousWorkPage.getTotalPages())
                .totalElements(previousWorkPage.getTotalElements())
                .build();

        return PaginatedResponse.<PreviousWorkDto>builder()
                .data(previousWorks)
                .meta(metadata)
                .build();
    }

    @Override
    public PreviousWorkDto getPreviousWorkById(Long id) {
        PreviousWork previousWork = previousWorkRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "previousWorkId", id));
        return mapToDto(previousWork);
    }

    @Override
    public PreviousWorkDto getPreviousWorkBySlug(String slug) {
        PreviousWork previousWork = previousWorkRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("PreviousWork", "slug", slug));
        return mapToDto(previousWork);
    }

    private PreviousWorkDto mapToDto(PreviousWork previousWork) {
        return PreviousWorkDto.builder()
                .previousWorkId(previousWork.getPreviousWorkId())
                .slug(previousWork.getSlug())
                .name(toBilingualField(previousWork.getName()))
                .summary(toBilingualField(previousWork.getSummary()))
                .caseName(toBilingualField(previousWork.getCaseName()))
                .caseCategory(toBilingualField(previousWork.getCaseCategory()))
                .caseFile(previousWork.getCaseFile())
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
