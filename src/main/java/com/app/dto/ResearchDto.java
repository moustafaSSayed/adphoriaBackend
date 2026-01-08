package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResearchDto {
    private Long researchId;
    private String researchPhoto;
    private String researchEnglishTitle;
    private String researchEnglishBody;
    private String englishShortDescription;
    private String researchArabicTitle;
    private String researchArabicBody;
    private String arabicShortDescription;
}
