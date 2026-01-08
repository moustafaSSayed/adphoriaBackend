package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "previous_works")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreviousWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "previous_work_id")
    private Long previousWorkId;

    @Column(name = "english_previous_work_name", nullable = false)
    private String englishPreviousWorkName;

    @Column(name = "english_summary", columnDefinition = "TEXT")
    private String englishSummary;

    @Column(name = "english_case_name")
    private String englishCaseName;

    @Column(name = "english_case_category")
    private String englishCaseCategory;

    @Column(name = "arabic_previous_work_name", nullable = false)
    private String arabicPreviousWorkName;

    @Column(name = "arabic_summary", columnDefinition = "TEXT")
    private String arabicSummary;

    @Column(name = "arabic_case_name")
    private String arabicCaseName;

    @Column(name = "arabic_case_category")
    private String arabicCaseCategory;

    @Column(name = "case_file")
    private String caseFile;
}
