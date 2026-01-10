package com.app.entity;

import com.app.util.SlugUtils;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "faqs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FAQ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_id")
    private Long questionId;

    @Column(unique = true)
    private String slug;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_question", nullable = false, columnDefinition = "TEXT")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_question", nullable = false, columnDefinition = "TEXT"))
    })
    private BilingualText question;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "en", column = @Column(name = "english_answer", nullable = false, columnDefinition = "TEXT")),
            @AttributeOverride(name = "ar", column = @Column(name = "arabic_answer", nullable = false, columnDefinition = "TEXT"))
    })
    private BilingualText answer;

    @PrePersist
    @PreUpdate
    private void generateSlug() {
        if (question != null && question.getEn() != null && !question.getEn().isEmpty()) {
            this.slug = SlugUtils.generateSlug(question.getEn());
        }
    }
}
