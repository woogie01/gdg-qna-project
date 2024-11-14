package org.example.qnaproject.answer.dto;

import lombok.Builder;
import org.example.qnaproject.answer.domain.Answer;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record AnswerResponse(
        String content,
        String author,
        Long authorId,
        LocalDateTime modifiedDate
) {
    public static List<AnswerResponse> toResponse(List<Answer> answers) {
        return answers.stream().map(answer -> AnswerResponse.builder()
                        .content(answer.getContent())
                        .authorId(answer.getAuthor().getId())
                        .author(answer.getAuthor().getUsername())
                        .build())
                .toList();
    }
}