package org.example.qnaproject.question.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.Valid;

@Valid
public record SearchDto(
        @Nullable
        String subject
) {
}