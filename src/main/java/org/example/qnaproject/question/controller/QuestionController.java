package org.example.qnaproject.question.controller;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.global.config.argumentresolver.Login;
import org.example.qnaproject.question.dto.QuestionRequest;
import org.example.qnaproject.question.service.QuestionService;
import org.example.qnaproject.user.dto.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/question")
public class QuestionController {

    private final QuestionService questionService;

    // 1. 단일 질문 조회

    // 2. 질문 등록
    @PostMapping
    public ResponseEntity<Long> createQuestion(@Login UserInfo userInfo, @RequestBody QuestionRequest questionRequest) {
        Long questionId = questionService.saveQuestion(userInfo, questionRequest);
        return ResponseEntity.ok(questionId);
    }

    // 3. 질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity<Long> modifyQuestion(@Login UserInfo userInfo, @PathVariable Long questionId, @RequestBody QuestionRequest questionRequest) {
        Long updatedQuestionId = questionService.modifyQuestion(userInfo, questionRequest, questionId);
        return ResponseEntity.ok(updatedQuestionId);
    }

    // 4. 질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity<Long> deleteQuestion(@Login UserInfo userInfo, @PathVariable Long questionId) {
        Long deletedQuestionId = questionService.deleteQuestion(userInfo, questionId);
        return ResponseEntity.ok(deletedQuestionId);
    }
}
