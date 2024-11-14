package org.example.qnaproject.answer.controller;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.answer.dto.AnswerRequest;
import org.example.qnaproject.answer.service.AnswerService;
import org.example.qnaproject.global.config.argumentresolver.Login;
import org.example.qnaproject.user.dto.UserInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/answer")
public class AnswerController {

    private final AnswerService answerService;

    // 1. 단일 답변 조회

    // 2. 답변 들록
    @PostMapping
    public ResponseEntity<Long> createAnswer(@Login UserInfo userInfo, @RequestBody AnswerRequest answerRequest) {
        Long answerId = answerService.saveAnswer(userInfo, answerRequest);
        return ResponseEntity.ok(answerId);
    }

    // 3. 답변 수정
    @PatchMapping("/{answerId}")
    public ResponseEntity<Long> modifyAnswer(@Login UserInfo userInfo, @PathVariable Long answerId, @RequestBody AnswerRequest answerRequest) {
        Long updateAnswerId = answerService.modifyAnswer(userInfo, answerRequest, answerId);
        return ResponseEntity.ok(updateAnswerId);
    }

    // 4. 답변 삭제
    @DeleteMapping("/{answerId}")
    public ResponseEntity<Long> deleteAnswer(@Login UserInfo userInfo, @PathVariable Long answerId) {
        Long deletedAnswerId = answerService.deleteAnswer(userInfo, answerId);
        return ResponseEntity.ok(deletedAnswerId);
    }
}
