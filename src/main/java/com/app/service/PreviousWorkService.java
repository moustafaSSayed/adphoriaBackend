package com.app.service;

import com.app.dto.PaginatedResponse;
import com.app.dto.PreviousWorkDto;

public interface PreviousWorkService {

    PreviousWorkDto createPreviousWork(PreviousWorkDto previousWorkDto);

    PreviousWorkDto updatePreviousWork(Long id, PreviousWorkDto previousWorkDto);

    void deletePreviousWork(Long id);

    PaginatedResponse<PreviousWorkDto> getAllPreviousWorks(int page, int size);

    PreviousWorkDto getPreviousWorkById(Long id);

    PreviousWorkDto getPreviousWorkBySlug(String slug);
}
