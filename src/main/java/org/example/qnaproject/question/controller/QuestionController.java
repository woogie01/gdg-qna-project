package org.example.qnaproject.question.controller;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.global.config.argumentresolver.Login;
import org.example.qnaproject.question.dto.QuestionRequest;
import org.example.qnaproject.question.dto.QuestionResponse;
import org.example.qnaproject.question.dto.SearchDto;
import org.example.qnaproject.question.service.QuestionService;
import org.example.qnaproject.user.dto.UserInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    // 1. 페이징
    @GetMapping
    public ResponseEntity<Slice<QuestionResponse>> getQuestionPage(
            @PageableDefault final Pageable pageable,
            @RequestBody SearchDto searchDto
            ) {
        Slice<QuestionResponse> questionPage = questionService.getQuestionPage(pageable, searchDto);
        return ResponseEntity.ok(questionPage);
    }

    // 2. 단일 질문 조회
    @GetMapping("/{questionId}")
    public ResponseEntity<QuestionResponse> getQuestion(@PathVariable Long questionId) {
        QuestionResponse response = questionService.getQuestion(questionId);
        return ResponseEntity.ok(response);
    }

    // 3. 질문 등록
    @PostMapping
    public ResponseEntity<Long> createQuestion(@Login UserInfo userInfo, @RequestBody QuestionRequest questionRequest) {
        Long questionId = questionService.saveQuestion(userInfo, questionRequest);
        return ResponseEntity.ok(questionId);
    }

    // 4. 질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity<Long> modifyQuestion(@Login UserInfo userInfo, @PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
        Long updatedQuestionId = questionService.modifyQuestion(userInfo, questionRequest, questionId);
        return ResponseEntity.ok(updatedQuestionId);
    }

    // 5. 질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Long> deleteQuestion(@Login UserInfo userInfo, @PathVariable Long questionId) {
        Long deletedQuestionId = questionService.deleteQuestion(userInfo, questionId);
        return ResponseEntity.ok(deletedQuestionId);
    }
}