package com.app.entity;

import com.app.util.SlugUtils;
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

    @Column(unique = true)
    private String slug;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_previous_work_name", nullable = false)),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_previous_work_name", nullable = false))
    })
    private BilingualText name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_summary", columnDefinition = "TEXT")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_summary", columnDefinition = "TEXT"))
    })
    private BilingualText summary;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_case_name")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_case_name"))
    })
    private BilingualText caseName;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_case_category")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_case_category"))
    })
    private BilingualText caseCategory;

    @Column(name = "case_file")
    private String caseFile;

    @PrePersist
    @PreUpdate
    private void generateSlug() {
        if (name != null && name.getEn() != null && !name.getEn().isEmpty()) {
            this.slug = SlugUtils.generateSlug(name.getEn());
        }
    }
}
