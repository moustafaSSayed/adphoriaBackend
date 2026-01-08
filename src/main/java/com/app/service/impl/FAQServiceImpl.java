package com.app.service.impl;

import com.app.dto.FAQDto;
import com.app.entity.FAQ;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.FAQRepository;
import com.app.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQServiceImpl implements FAQService {

    private final FAQRepository faqRepository;

    @Override
    public FAQDto createFAQ(FAQDto faqDto) {
        FAQ faq = FAQ.builder()
                .englishQuestion(faqDto.getEnglishQuestion())
                .englishAnswer(faqDto.getEnglishAnswer())
                .arabicQuestion(faqDto.getArabicQuestion())
                .arabicAnswer(faqDto.getArabicAnswer())
                .build();

        FAQ savedFAQ = faqRepository.save(faq);
        return mapToDto(savedFAQ);
    }

    @Override
    public FAQDto updateFAQ(Long id, FAQDto faqDto) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));

        // Only update fields that are not null (partial update support)
        if (faqDto.getEnglishQuestion() != null) {
            faq.setEnglishQuestion(faqDto.getEnglishQuestion());
        }
        if (faqDto.getEnglishAnswer() != null) {
            faq.setEnglishAnswer(faqDto.getEnglishAnswer());
        }
        if (faqDto.getArabicQuestion() != null) {
            faq.setArabicQuestion(faqDto.getArabicQuestion());
        }
        if (faqDto.getArabicAnswer() != null) {
            faq.setArabicAnswer(faqDto.getArabicAnswer());
        }

        FAQ updatedFAQ = faqRepository.save(faq);
        return mapToDto(updatedFAQ);
    }

    @Override
    public void deleteFAQ(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));
        faqRepository.delete(faq);
    }

    @Override
    public List<FAQDto> getAllFAQs() {
        return faqRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public FAQDto getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));
        return mapToDto(faq);
    }

    private FAQDto mapToDto(FAQ faq) {
        return FAQDto.builder()
                .questionId(faq.getQuestionId())
                .englishQuestion(faq.getEnglishQuestion())
                .englishAnswer(faq.getEnglishAnswer())
                .arabicQuestion(faq.getArabicQuestion())
                .arabicAnswer(faq.getArabicAnswer())
                .build();
    }
}
