package com.app.entity;

import com.app.util.SlugUtils;
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

    @Column(unique = true)
    private String slug;

    @Column(name = "blog_photo")
    private String blogPhoto;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "blog_english_title", nullable = false)),
            @AttributeOverride(name = "ar", column = @Column(name = "blog_arabic_title", nullable = false))
    })
    private BilingualText title;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "blog_english_body", columnDefinition = "TEXT")),
            @AttributeOverride(name = "ar", column = @Column(name = "blog_arabic_body", columnDefinition = "TEXT"))
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
