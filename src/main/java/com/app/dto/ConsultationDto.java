package com.app.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ConsultationDto {
    private Long consultationId;
    private String consultationStatus;
    private String firstName;
    private String lastName;
    private String causeCategory;
    private String causeName;
    private String mobileNumber;
    private String email;
    private String consultationBody;
}
