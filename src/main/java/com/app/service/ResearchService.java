package com.app.service;

import com.app.dto.PaginatedResponse;
import com.app.dto.ResearchDto;

public interface ResearchService {

    ResearchDto createResearch(ResearchDto researchDto);

    ResearchDto updateResearch(Long id, ResearchDto researchDto);

    void deleteResearch(Long id);

    PaginatedResponse<ResearchDto> getAllResearch(int page, int size);

    ResearchDto getResearchById(Long id);

    ResearchDto getResearchBySlug(String slug);
}
