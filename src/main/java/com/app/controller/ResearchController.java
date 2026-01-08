package com.app.controller;

import com.app.dto.ResearchDto;
import com.app.service.ResearchService;
import com.app.service.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/researches")
@RequiredArgsConstructor
public class ResearchController {

    private final ResearchService researchService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResearchDto> createResearch(
            @RequestParam(value = "researchPhoto", required = false) MultipartFile researchPhoto,
            @RequestParam("researchEnglishTitle") String researchEnglishTitle,
            @RequestParam("researchEnglishBody") String researchEnglishBody,
            @RequestParam(value = "englishShortDescription", required = false) String englishShortDescription,
            @RequestParam("researchArabicTitle") String researchArabicTitle,
            @RequestParam("researchArabicBody") String researchArabicBody,
            @RequestParam(value = "arabicShortDescription", required = false) String arabicShortDescription) {

        String photoUrl = null;
        if (researchPhoto != null && !researchPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(researchPhoto);
        }

        ResearchDto researchDto = ResearchDto.builder()
                .researchPhoto(photoUrl)
                .researchEnglishTitle(researchEnglishTitle)
                .researchEnglishBody(researchEnglishBody)
                .englishShortDescription(englishShortDescription)
                .researchArabicTitle(researchArabicTitle)
                .researchArabicBody(researchArabicBody)
                .arabicShortDescription(arabicShortDescription)
                .build();

        ResearchDto created = researchService.createResearch(researchDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResearchDto> updateResearch(
            @PathVariable Long id,
            @RequestParam(value = "researchPhoto", required = false) MultipartFile researchPhoto,
            @RequestParam(value = "researchEnglishTitle", required = false) String researchEnglishTitle,
            @RequestParam(value = "researchEnglishBody", required = false) String researchEnglishBody,
            @RequestParam(value = "englishShortDescription", required = false) String englishShortDescription,
            @RequestParam(value = "researchArabicTitle", required = false) String researchArabicTitle,
            @RequestParam(value = "researchArabicBody", required = false) String researchArabicBody,
            @RequestParam(value = "arabicShortDescription", required = false) String arabicShortDescription) {

        String photoUrl = null;
        if (researchPhoto != null && !researchPhoto.isEmpty()) {
            photoUrl = fileStorageService.storeFile(researchPhoto);
        }

        ResearchDto researchDto = ResearchDto.builder()
                .researchPhoto(photoUrl)
                .researchEnglishTitle(researchEnglishTitle)
                .researchEnglishBody(researchEnglishBody)
                .englishShortDescription(englishShortDescription)
                .researchArabicTitle(researchArabicTitle)
                .researchArabicBody(researchArabicBody)
                .arabicShortDescription(arabicShortDescription)
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
    public ResponseEntity<List<ResearchDto>> getAllResearches() {
        List<ResearchDto> researches = researchService.getAllResearches();
        return ResponseEntity.ok(researches);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResearchDto> getResearchById(@PathVariable Long id) {
        ResearchDto research = researchService.getResearchById(id);
        return ResponseEntity.ok(research);
    }
}
