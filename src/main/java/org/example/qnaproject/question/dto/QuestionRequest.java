package org.example.qnaproject.question.dto;

public record QuestionRequest(
        String subject,
        String content
) {
}