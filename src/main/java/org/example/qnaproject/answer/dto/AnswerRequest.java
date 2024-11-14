package org.example.qnaproject.answer.dto;

public record AnswerRequest(
        Long questionId,
        String content
) {
}
