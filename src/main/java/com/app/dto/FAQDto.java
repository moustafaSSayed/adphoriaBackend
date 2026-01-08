package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQDto {
    private Long questionId;
    private String englishQuestion;
    private String englishAnswer;
    private String arabicQuestion;
    private String arabicAnswer;
}
