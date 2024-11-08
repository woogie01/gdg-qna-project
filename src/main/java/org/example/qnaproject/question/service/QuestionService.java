package org.example.qnaproject.question.service;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.question.domain.Question;
import org.example.qnaproject.question.dto.QuestionRequest;
import org.example.qnaproject.question.repository.QuestionRepository;
import org.example.qnaproject.user.domain.User;
import org.example.qnaproject.user.dto.UserInfo;
import org.example.qnaproject.user.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    // TODO : 페이징

    // 1, 단일 질문 조회

    // 2. 질문 저장
    public Long saveQuestion(UserInfo userInfo, QuestionRequest questionRequest) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Question question = new Question(questionRequest.subject(), questionRequest.content(), author);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    // 3. 질문 수정
    public Long modifyQuestion(UserInfo userInfo, QuestionRequest questionRequest, Long questionId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 회원입니다."));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }

    // 4. 질문 삭제
    public Long deleteQuestion(UserInfo userInfo, Long questionId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new RuntimeException("회원을 찾을 수 없습니다."));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new RuntimeException("존재하지 않는 질문입니다."));
    }
}