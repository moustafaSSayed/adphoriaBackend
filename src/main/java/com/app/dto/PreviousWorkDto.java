package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreviousWorkDto {
    private Long previousWorkId;
    private String englishPreviousWorkName;
    private String englishSummary;
    private String englishCaseName;
    private String englishCaseCategory;
    private String arabicPreviousWorkName;
    private String arabicSummary;
    private String arabicCaseName;
    private String arabicCaseCategory;
    private String caseFile;
}
