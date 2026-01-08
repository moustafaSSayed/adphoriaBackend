package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "researches")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Research {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "research_id")
    private Long researchId;

    @Column(name = "research_photo")
    private String researchPhoto;

    @Column(name = "research_english_title", nullable = false)
    private String researchEnglishTitle;

    @Column(name = "research_english_body", columnDefinition = "TEXT")
    private String researchEnglishBody;

    @Column(name = "english_short_description")
    private String englishShortDescription;

    @Column(name = "research_arabic_title", nullable = false)
    private String researchArabicTitle;

    @Column(name = "research_arabic_body", columnDefinition = "TEXT")
    private String researchArabicBody;

    @Column(name = "arabic_short_description")
    private String arabicShortDescription;
}
