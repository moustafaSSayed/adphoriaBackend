package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQDto {
    private Long questionId;
    private String slug;
    private BilingualField question;
    private BilingualField answer;
}
