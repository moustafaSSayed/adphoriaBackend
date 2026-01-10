package com.app.entity;

import com.app.util.SlugUtils;
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

    @Column(unique = true)
    private String slug;

    @Column(name = "research_photo")
    private String researchPhoto;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "research_english_title", nullable = false)),
            @AttributeOverride(name = "ar", column = @Column(name = "research_arabic_title", nullable = false))
    })
    private BilingualText title;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "research_english_body", columnDefinition = "TEXT")),
            @AttributeOverride(name = "ar", column = @Column(name = "research_arabic_body", columnDefinition = "TEXT"))
    })
    private BilingualText body;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_short_description")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_short_description"))
    })
    private BilingualText shortDescription;

    @PrePersist
    @PreUpdate
    private void generateSlug() {
        if (title != null && title.getEn() != null && !title.getEn().isEmpty()) {
            this.slug = SlugUtils.generateSlug(title.getEn());
        }
    }
}
