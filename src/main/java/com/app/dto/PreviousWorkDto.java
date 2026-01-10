package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreviousWorkDto {
    private Long previousWorkId;
    private String slug;
    private BilingualField name;
    private BilingualField summary;
    private BilingualField caseName;
    private BilingualField caseCategory;
    private String caseFile;
}
