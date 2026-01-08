package com.app.entity;

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

    @Column(name = "english_question", nullable = false, columnDefinition = "TEXT")
    private String englishQuestion;

    @Column(name = "english_answer", nullable = false, columnDefinition = "TEXT")
    private String englishAnswer;

    @Column(name = "arabic_question", nullable = false, columnDefinition = "TEXT")
    private String arabicQuestion;

    @Column(name = "arabic_answer", nullable = false, columnDefinition = "TEXT")
    private String arabicAnswer;
}
