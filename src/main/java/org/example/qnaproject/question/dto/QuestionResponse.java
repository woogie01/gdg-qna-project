package org.example.qnaproject.question.dto;

import lombok.Builder;
import org.example.qnaproject.answer.dto.AnswerResponse;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record QuestionResponse(
        String subject,
        String content,
        Long authorId,
        String author,
        List<AnswerResponse> answers,
        LocalDateTime modifiedDate
) {
}