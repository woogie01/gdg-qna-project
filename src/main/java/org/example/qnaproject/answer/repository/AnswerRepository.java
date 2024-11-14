package org.example.qnaproject.answer.repository;

import org.example.qnaproject.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
