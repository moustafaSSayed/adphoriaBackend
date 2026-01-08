package com.app.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "consultations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consultation_id")
    private Long consultationId;

    @Column(name = "consultation_status")
    private String consultationStatus;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "cause_category")
    private String causeCategory;

    @Column(name = "cause_name")
    private String causeName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(name = "consultation_body", columnDefinition = "TEXT")
    private String consultationBody;
}
