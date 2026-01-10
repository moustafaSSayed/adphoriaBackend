package com.app.service;

import com.app.dto.FAQDto;
import com.app.dto.PaginatedResponse;

public interface FAQService {

    FAQDto createFAQ(FAQDto faqDto);

    FAQDto updateFAQ(Long id, FAQDto faqDto);

    void deleteFAQ(Long id);

    PaginatedResponse<FAQDto> getAllFAQs(int page, int size);

    FAQDto getFAQById(Long id);

    FAQDto getFAQBySlug(String slug);
}
