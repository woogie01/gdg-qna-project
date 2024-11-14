package org.example.qnaproject.question.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.qnaproject.answer.domain.Answer;
import org.example.qnaproject.global.BaseEntity;
import org.example.qnaproject.user.domain.User;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA 표준 스펙에 디폴트 생성자가 필요
public class Question extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String subject; // 제목

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content; // 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author; // 작성자

    /**
     * 답변 리스트 매핑
     */
    @OneToMany(mappedBy = "question")
    private List<Answer> answers = new ArrayList<>();

    public Question(String subject, String content, User author) {
        this.subject = subject;
        this.content = content;
        this.author = author;
    }

    public void update(String subject, String content) {
        this.subject = subject;
        this.content = content;
    }

    // 답변 추가 연동
    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    // 답변 삭제 연동
    public void removeAnswer(Answer answer) {
        answers.remove(answer);
    }
}