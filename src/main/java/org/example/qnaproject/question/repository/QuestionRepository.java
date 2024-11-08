package org.example.qnaproject.question.repository;

import org.example.qnaproject.question.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}