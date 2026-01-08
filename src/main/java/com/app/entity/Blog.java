package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blogs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Blog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long blogId;
    @Column(name = "blog_photo")
    private String blogPhoto;

    @Column(name = "blog_english_title", nullable = false)
    private String blogEnglishTitle;

    @Column(name = "blog_english_body", columnDefinition = "TEXT")
    private String blogEnglishBody;

    @Column(name = "english_short_description")
    private String englishShortDescription;

    @Column(name = "blog_arabic_title", nullable = false)
    private String blogArabicTitle;

    @Column(name = "blog_arabic_body", columnDefinition = "TEXT")
    private String blogArabicBody;

    @Column(name = "arabic_short_description")
    private String arabicShortDescription;
}
