package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogDto {
    private Long blogId;
    private String blogPhoto;
    private String blogEnglishTitle;
    private String blogEnglishBody;
    private String englishShortDescription;
    private String blogArabicTitle;
    private String blogArabicBody;
    private String arabicShortDescription;
}
