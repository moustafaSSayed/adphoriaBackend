package com.app.controller;

import com.app.dto.FAQDto;
import com.app.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faqs")
@RequiredArgsConstructor
public class FAQController {

    private final FAQService faqService;

    @PostMapping
    public ResponseEntity<FAQDto> createFAQ(@RequestBody FAQDto faqDto) {
        FAQDto created = faqService.createFAQ(faqDto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FAQDto> updateFAQ(@PathVariable Long id, @RequestBody FAQDto faqDto) {
        FAQDto updated = faqService.updateFAQ(id, faqDto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFAQ(@PathVariable Long id) {
        faqService.deleteFAQ(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FAQDto>> getAllFAQs() {
        List<FAQDto> faqs = faqService.getAllFAQs();
        return ResponseEntity.ok(faqs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FAQDto> getFAQById(@PathVariable Long id) {
        FAQDto faq = faqService.getFAQById(id);
        return ResponseEntity.ok(faq);
    }
}
