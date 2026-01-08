package com.app.service;

import com.app.dto.ResearchDto;
import java.util.List;

public interface ResearchService {

    ResearchDto createResearch(ResearchDto researchDto);

    ResearchDto updateResearch(Long id, ResearchDto researchDto);

    void deleteResearch(Long id);

    List<ResearchDto> getAllResearches();

    ResearchDto getResearchById(Long id);
}
