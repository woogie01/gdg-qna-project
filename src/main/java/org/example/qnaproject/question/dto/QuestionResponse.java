package org.example.qnaproject.question.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author,
        LocalDateTime modifiedDate
) {
}