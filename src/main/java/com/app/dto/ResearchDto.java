package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResearchDto {
    private Long researchId;
    private String slug;
    private String researchPhoto;
    private BilingualField title;
    private BilingualField body;
    private BilingualField shortDescription;
}
