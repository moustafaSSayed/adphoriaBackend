package com.app.controller;

import com.app.dto.PreviousWorkDto;
import com.app.service.PreviousWorkService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/previous-works")
@RequiredArgsConstructor
public class PreviousWorkController {

    private final PreviousWorkService previousWorkService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PreviousWorkDto> createPreviousWork(
            @RequestParam("englishPreviousWorkName") String englishPreviousWorkName,
            @RequestParam(value = "englishSummary", required = false) String englishSummary,
            @RequestParam(value = "englishCaseName", required = false) String englishCaseName,
            @RequestParam(value = "englishCaseCategory", required = false) String englishCaseCategory,
            @RequestParam("arabicPreviousWorkName") String arabicPreviousWorkName,
            @RequestParam(value = "arabicSummary", required = false) String arabicSummary,
            @RequestParam(value = "arabicCaseName", required = false) String arabicCaseName,
            @RequestParam(value = "arabicCaseCategory", required = false) String arabicCaseCategory,
            @RequestParam(value = "caseFile", required = false) MultipartFile caseFile) {

        String fileUrl = null;
        if (caseFile != null && !caseFile.isEmpty()) {
            fileUrl = fileStorageService.storeFile(caseFile);
        }

        PreviousWorkDto dto = PreviousWorkDto.builder()
                .englishPreviousWorkName(englishPreviousWorkName)
                .englishSummary(englishSummary)
                .englishCaseName(englishCaseName)
                .englishCaseCategory(englishCaseCategory)
                .arabicPreviousWorkName(arabicPreviousWorkName)
                .arabicSummary(arabicSummary)
                .arabicCaseName(arabicCaseName)
                .arabicCaseCategory(arabicCaseCategory)
                .caseFile(fileUrl)
                .build();

        PreviousWorkDto created = previousWorkService.createPreviousWork(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PreviousWorkDto> updatePreviousWork(
            @PathVariable Long id,
            @RequestParam(value = "englishPreviousWorkName", required = false) String englishPreviousWorkName,
            @RequestParam(value = "englishSummary", required = false) String englishSummary,
            @RequestParam(value = "englishCaseName", required = false) String englishCaseName,
            @RequestParam(value = "englishCaseCategory", required = false) String englishCaseCategory,
            @RequestParam(value = "arabicPreviousWorkName", required = false) String arabicPreviousWorkName,
            @RequestParam(value = "arabicSummary", required = false) String arabicSummary,
            @RequestParam(value = "arabicCaseName", required = false) String arabicCaseName,
            @RequestParam(value = "arabicCaseCategory", required = false) String arabicCaseCategory,
            @RequestParam(value = "caseFile", required = false) MultipartFile caseFile) {

        String fileUrl = null;
        if (caseFile != null && !caseFile.isEmpty()) {
            fileUrl = fileStorageService.storeFile(caseFile);
        }

        PreviousWorkDto dto = PreviousWorkDto.builder()
                .englishPreviousWorkName(englishPreviousWorkName)
                .englishSummary(englishSummary)
                .englishCaseName(englishCaseName)
                .englishCaseCategory(englishCaseCategory)
                .arabicPreviousWorkName(arabicPreviousWorkName)
                .arabicSummary(arabicSummary)
                .arabicCaseName(arabicCaseName)
                .arabicCaseCategory(arabicCaseCategory)
                .caseFile(fileUrl)
                .build();

        PreviousWorkDto updated = previousWorkService.updatePreviousWork(id, dto);
        return ResponseEntity.ok(updated);
    }

    // JSON endpoint for update without file upload
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PreviousWorkDto> updatePreviousWorkJson(@PathVariable Long id,
            @RequestBody PreviousWorkDto dto) {
        PreviousWorkDto updated = previousWorkService.updatePreviousWork(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePreviousWork(@PathVariable Long id) {
        previousWorkService.deletePreviousWork(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PreviousWorkDto>> getAllPreviousWorks() {
        List<PreviousWorkDto> previousWorks = previousWorkService.getAllPreviousWorks();
        return ResponseEntity.ok(previousWorks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreviousWorkDto> getPreviousWorkById(@PathVariable Long id) {
        PreviousWorkDto previousWork = previousWorkService.getPreviousWorkById(id);
        return ResponseEntity.ok(previousWork);
    }
}
