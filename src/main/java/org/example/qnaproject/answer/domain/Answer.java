package org.example.qnaproject.answer.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.qnaproject.global.BaseEntity;
import org.example.qnaproject.question.domain.Question;
import org.example.qnaproject.user.domain.User;

@Entity
@Getter
@Table(name = "answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Question question; // 질문

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private User author; // 작성자

    public Answer(String content, Question question, User author) {
        this.content = content;
        this.question = question;
        this.author = author;
    }

    public void update(String content) {
        this.content = content;
    }
}