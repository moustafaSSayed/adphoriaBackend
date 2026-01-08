package com.app.service;

import com.app.dto.PreviousWorkDto;
import java.util.List;

public interface PreviousWorkService {

    PreviousWorkDto createPreviousWork(PreviousWorkDto previousWorkDto);

    PreviousWorkDto updatePreviousWork(Long id, PreviousWorkDto previousWorkDto);

    void deletePreviousWork(Long id);

    List<PreviousWorkDto> getAllPreviousWorks();

    PreviousWorkDto getPreviousWorkById(Long id);
}
