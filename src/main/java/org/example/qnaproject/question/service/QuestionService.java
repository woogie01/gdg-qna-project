package org.example.qnaproject.question.service;

import lombok.RequiredArgsConstructor;
import org.example.qnaproject.answer.domain.Answer;
import org.example.qnaproject.answer.dto.AnswerResponse;
import org.example.qnaproject.global.exception.BusinessException;
import org.example.qnaproject.global.exception.ErrorCode;
import org.example.qnaproject.question.domain.Question;
import org.example.qnaproject.question.dto.QuestionRequest;
import org.example.qnaproject.question.dto.QuestionResponse;
import org.example.qnaproject.question.dto.SearchDto;
import org.example.qnaproject.question.repository.QuestionQueryRepository;
import org.example.qnaproject.question.repository.QuestionRepository;
import org.example.qnaproject.user.domain.User;
import org.example.qnaproject.user.dto.UserInfo;
import org.example.qnaproject.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class QuestionService {

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;
    private final QuestionQueryRepository queryRepository;

    // 1. 페이징
    @Transactional
    public Slice<QuestionResponse> getQuestionPage(
            Pageable pageable,
            SearchDto searchDto
    ) {
        if (Objects.isNull(searchDto.subject())) {
            return queryRepository.getQuestionResponses(pageable);
        } else {
            return queryRepository.getQuestionResponses(pageable, searchDto);
        }
    }

    // 2, 단일 질문 조회
    public QuestionResponse getQuestion(Long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));

        List<Answer> answers = question.getAnswers();
        List<AnswerResponse> answerResponses = AnswerResponse.toResponses(answers);

        return QuestionResponse.builder()
                .subject(question.getSubject())
                .content(question.getContent())
                .authorId(question.getAuthor().getId())
                .author(question.getAuthor().getUsername())
                .answers(answerResponses)
                .modifiedDate(question.getModifiedDate())
                .build();
    }

    // 3. 질문 저장
    public Long saveQuestion(UserInfo userInfo, QuestionRequest questionRequest) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = new Question(questionRequest.subject(), questionRequest.content(), author);
        Question savedQuestion = questionRepository.save(question);
        return savedQuestion.getId();
    }

    // 4. 질문 수정
    public Long modifyQuestion(UserInfo userInfo, QuestionRequest questionRequest, Long questionId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));
        question.update(questionRequest.subject(), questionRequest.content());
        questionRepository.save(question);
        return question.getId();
    }

    // 5. 질문 삭제
    public Long deleteQuestion(UserInfo userInfo, Long questionId) {
        User author = userRepository.findById(userInfo.getId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_USER_EXCEPTION));
        return questionRepository.findById(questionId)
                .map(question -> {
                    questionRepository.delete(question);
                    return question.getId();
                })
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_EXIST_QUESTION_EXCEPTION));
    }
}