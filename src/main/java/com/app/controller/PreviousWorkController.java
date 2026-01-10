package com.app.controller;

import com.app.dto.BilingualField;
import com.app.dto.PaginatedResponse;
import com.app.dto.PreviousWorkDto;
import com.app.service.PreviousWorkService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/previous-works")
@RequiredArgsConstructor
public class PreviousWorkController {

    private final PreviousWorkService previousWorkService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PreviousWorkDto> createPreviousWork(
            @RequestParam("nameEn") String nameEn,
            @RequestParam("nameAr") String nameAr,
            @RequestParam(value = "summaryEn", required = false) String summaryEn,
            @RequestParam(value = "summaryAr", required = false) String summaryAr,
            @RequestParam(value = "caseNameEn", required = false) String caseNameEn,
            @RequestParam(value = "caseNameAr", required = false) String caseNameAr,
            @RequestParam(value = "caseCategoryEn", required = false) String caseCategoryEn,
            @RequestParam(value = "caseCategoryAr", required = false) String caseCategoryAr,
            @RequestParam(value = "caseFile", required = false) MultipartFile caseFile) {

        String fileUrl = null;
        if (caseFile != null && !caseFile.isEmpty()) {
            fileUrl = fileStorageService.storeFile(caseFile);
        }

        PreviousWorkDto dto = PreviousWorkDto.builder()
                .name(BilingualField.builder().en(nameEn).ar(nameAr).build())
                .summary(BilingualField.builder().en(summaryEn).ar(summaryAr).build())
                .caseName(BilingualField.builder().en(caseNameEn).ar(caseNameAr).build())
                .caseCategory(BilingualField.builder().en(caseCategoryEn).ar(caseCategoryAr).build())
                .caseFile(fileUrl)
                .build();

        PreviousWorkDto created = previousWorkService.createPreviousWork(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PreviousWorkDto> updatePreviousWork(
            @PathVariable Long id,
            @RequestParam(value = "nameEn", required = false) String nameEn,
            @RequestParam(value = "nameAr", required = false) String nameAr,
            @RequestParam(value = "summaryEn", required = false) String summaryEn,
            @RequestParam(value = "summaryAr", required = false) String summaryAr,
            @RequestParam(value = "caseNameEn", required = false) String caseNameEn,
            @RequestParam(value = "caseNameAr", required = false) String caseNameAr,
            @RequestParam(value = "caseCategoryEn", required = false) String caseCategoryEn,
            @RequestParam(value = "caseCategoryAr", required = false) String caseCategoryAr,
            @RequestParam(value = "caseFile", required = false) MultipartFile caseFile) {

        String fileUrl = null;
        if (caseFile != null && !caseFile.isEmpty()) {
            fileUrl = fileStorageService.storeFile(caseFile);
        }

        PreviousWorkDto dto = PreviousWorkDto.builder()
                .name(nameEn != null ? BilingualField.builder().en(nameEn).ar(nameAr).build() : null)
                .summary(summaryEn != null ? BilingualField.builder().en(summaryEn).ar(summaryAr).build() : null)
                .caseName(caseNameEn != null ? BilingualField.builder().en(caseNameEn).ar(caseNameAr).build() : null)
                .caseCategory(
                        caseCategoryEn != null ? BilingualField.builder().en(caseCategoryEn).ar(caseCategoryAr).build()
                                : null)
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
    public ResponseEntity<PaginatedResponse<PreviousWorkDto>> getAllPreviousWorks(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) {
        PaginatedResponse<PreviousWorkDto> previousWorks = previousWorkService.getAllPreviousWorks(page, size);
        return ResponseEntity.ok(previousWorks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PreviousWorkDto> getPreviousWorkById(@PathVariable Long id) {
        PreviousWorkDto previousWork = previousWorkService.getPreviousWorkById(id);
        return ResponseEntity.ok(previousWork);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<PreviousWorkDto> getPreviousWorkBySlug(@PathVariable String slug) {
        PreviousWorkDto previousWork = previousWorkService.getPreviousWorkBySlug(slug);
        return ResponseEntity.ok(previousWork);
    }
}
