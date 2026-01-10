package com.app.controller;

import com.app.dto.BilingualField;
import com.app.dto.PaginatedResponse;
import com.app.dto.ResearchDto;
import com.app.service.ResearchService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/researches")
@RequiredArgsConstructor
public class ResearchController {

    private final ResearchService researchService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResearchDto> createResearch(
            @RequestParam(value = "researchPhoto", required = false) MultipartFile researchPhoto,
            @RequestParam("titleEn") String titleEn,
            @RequestParam("titleAr") String titleAr,
            @RequestParam("bodyEn") String bodyEn,
            @RequestParam("bodyAr") String bodyAr,
            @RequestParam(value = "shortDescriptionEn", required = false) String shortDescriptionEn,
            @RequestParam(value = "shortDescriptionAr", required = false) String shortDescriptionAr) {

        String photoUrl = null;
        if (researchPhoto != null && !researchPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(researchPhoto);
        }

        ResearchDto researchDto = ResearchDto.builder()
                .researchPhoto(photoUrl)
                .title(BilingualField.builder().en(titleEn).ar(titleAr).build())
                .body(BilingualField.builder().en(bodyEn).ar(bodyAr).build())
                .shortDescription(BilingualField.builder().en(shortDescriptionEn).ar(shortDescriptionAr).build())
                .build();

        ResearchDto created = researchService.createResearch(researchDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResearchDto> updateResearch(
            @PathVariable Long id,
            @RequestParam(value = "researchPhoto", required = false) MultipartFile researchPhoto,
            @RequestParam(value = "titleEn", required = false) String titleEn,
            @RequestParam(value = "titleAr", required = false) String titleAr,
            @RequestParam(value = "bodyEn", required = false) String bodyEn,
            @RequestParam(value = "bodyAr", required = false) String bodyAr,
            @RequestParam(value = "shortDescriptionEn", required = false) String shortDescriptionEn,
            @RequestParam(value = "shortDescriptionAr", required = false) String shortDescriptionAr) {

        String photoUrl = null;
        if (researchPhoto != null && !researchPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(researchPhoto);
        }

        ResearchDto researchDto = ResearchDto.builder()
                .researchPhoto(photoUrl)
                .title(titleEn != null ? BilingualField.builder().en(titleEn).ar(titleAr).build() : null)
                .body(bodyEn != null ? BilingualField.builder().en(bodyEn).ar(bodyAr).build() : null)
                .shortDescription(shortDescriptionEn != null
                        ? BilingualField.builder().en(shortDescriptionEn).ar(shortDescriptionAr).build()
                        : null)
                .build();

        ResearchDto updated = researchService.updateResearch(id, researchDto);
        return ResponseEntity.ok(updated);
    }

    // JSON endpoint for update without file upload
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResearchDto> updateResearchJson(@PathVariable Long id, @RequestBody ResearchDto researchDto) {
        ResearchDto updated = researchService.updateResearch(id, researchDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResearch(@PathVariable Long id) {
        researchService.deleteResearch(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<PaginatedResponse<ResearchDto>> getAllResearches(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "15") int size) {
        PaginatedResponse<ResearchDto> researches = researchService.getAllResearch(page, size);
        return ResponseEntity.ok(researches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchDto> getResearchById(@PathVariable Long id) {
        ResearchDto research = researchService.getResearchById(id);
        return ResponseEntity.ok(research);
    }

    @GetMapping("/slug/{slug}")
    public ResponseEntity<ResearchDto> getResearchBySlug(@PathVariable String slug) {
        ResearchDto research = researchService.getResearchBySlug(slug);
        return ResponseEntity.ok(research);
    }
}
