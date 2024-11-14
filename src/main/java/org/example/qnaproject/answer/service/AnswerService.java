package org.example.qnaproject.answer.service;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.answer.domain.Answer;
import org.example.qnaproject.answer.dto.AnswerRequest;
import org.example.qnaproject.answer.repository.AnswerRepository;
import org.example.qnaproject.global.exception.BusinessException;
import org.example.qnaproject.global.exception.ErrorCode;
import org.example.qnaproject.question.domain.Question;
import org.example.qnaproject.question.repository.QuestionRepository;
import org.example.qnaproject.user.domain.User;
import org.example.qnaproject.user.dto.UserInfo;
import org.example.qnaproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    // 1. 답변 저장
    public Long saveAnswer(UserInfo userInfo, AnswerRequest answerRequest) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = questionRepository.findById(answerRequest.questionId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));
        Answer answer = new Answer(answerRequest.content(), question, author);
        Answer savedAnswer = answerRepository.save(answer);
        question.addAnswer(savedAnswer);
        return savedAnswer.getId();
    }

    // 2. 답변 수정
    public Long modifyAnswer(UserInfo userInfo, AnswerRequest answerRequest, Long answerId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Answer answer = answerRepository.findById(answerId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ANSWER_EXCEPTION));
        answer.update(answerRequest.content());
        return answer.getId();
    }

    // 3. 답변 삭제
    public Long deleteAnswer(UserInfo userInfo, Long answerId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        return answerRepository.findById(answerId)
                .map(answer -> {
                    answerRepository.delete(answer);
                    return answer.getId();
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_ANSWER_EXCEPTION));
    }
}