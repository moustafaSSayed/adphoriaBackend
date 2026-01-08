package com.app.service;

import com.app.dto.FAQDto;
import java.util.List;

public interface FAQService {

    FAQDto createFAQ(FAQDto faqDto);

    FAQDto updateFAQ(Long id, FAQDto faqDto);

    void deleteFAQ(Long id);

    List<FAQDto> getAllFAQs();

    FAQDto getFAQById(Long id);
}
