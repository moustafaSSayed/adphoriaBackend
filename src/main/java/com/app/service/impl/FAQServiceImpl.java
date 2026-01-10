package com.app.service.impl;

import com.app.dto.BilingualField;
import com.app.dto.FAQDto;
import com.app.dto.PaginatedResponse;
import com.app.entity.BilingualText;
import com.app.entity.FAQ;
import com.app.exception.ResourceNotFoundException;
import com.app.repository.FAQRepository;
import com.app.service.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
                .question(toBilingualText(faqDto.getQuestion()))
                .answer(toBilingualText(faqDto.getAnswer()))
                .build();

        FAQ saved = faqRepository.save(faq);
        return mapToDto(saved);
    }

    @Override
    public FAQDto updateFAQ(Long id, FAQDto faqDto) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));

        if (faqDto.getQuestion() != null) {
            faq.setQuestion(mergeBilingualText(faq.getQuestion(), faqDto.getQuestion()));
        }
        if (faqDto.getAnswer() != null) {
            faq.setAnswer(mergeBilingualText(faq.getAnswer(), faqDto.getAnswer()));
        }

        FAQ updated = faqRepository.save(faq);
        return mapToDto(updated);
    }

    @Override
    public void deleteFAQ(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));
        faqRepository.delete(faq);
    }

    @Override
    public PaginatedResponse<FAQDto> getAllFAQs(int page, int size) {
        Page<FAQ> faqPage = faqRepository.findAll(PageRequest.of(page - 1, size));

        List<FAQDto> faqs = faqPage.getContent().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());

        PaginatedResponse.PageMetadata metadata = PaginatedResponse.PageMetadata.builder()
                .currentPage(page)
                .pageSize(size)
                .totalPages(faqPage.getTotalPages())
                .totalElements(faqPage.getTotalElements())
                .build();

        return PaginatedResponse.<FAQDto>builder()
                .data(faqs)
                .meta(metadata)
                .build();
    }

    @Override
    public FAQDto getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "questionId", id));
        return mapToDto(faq);
    }

    @Override
    public FAQDto getFAQBySlug(String slug) {
        FAQ faq = faqRepository.findBySlug(slug)
                .orElseThrow(() -> new ResourceNotFoundException("FAQ", "slug", slug));
        return mapToDto(faq);
    }

    private FAQDto mapToDto(FAQ faq) {
        return FAQDto.builder()
                .questionId(faq.getQuestionId())
                .slug(faq.getSlug())
                .question(toBilingualField(faq.getQuestion()))
                .answer(toBilingualField(faq.getAnswer()))
                .build();
    }

    private BilingualText toBilingualText(BilingualField field) {
        if (field == null)
            return null;
        return BilingualText.builder()
                .en(field.getEn())
                .ar(field.getAr())
                .build();
    }

    private BilingualText mergeBilingualText(BilingualText existing, BilingualField updates) {
        if (updates == null)
            return existing;
        if (existing == null)
            return toBilingualText(updates);

        return BilingualText.builder()
                .en(updates.getEn() != null ? updates.getEn() : existing.getEn())
                .ar(updates.getAr() != null ? updates.getAr() : existing.getAr())
                .build();
    }

    private BilingualField toBilingualField(BilingualText text) {
        if (text == null)
            return null;
        return BilingualField.builder()
                .en(text.getEn())
                .ar(text.getAr())
                .build();
    }
}
