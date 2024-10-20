package com.overmind.overmind_chatbot.service;

import com.overmind.overmind_chatbot.dto.QuestionRecordDTO;
import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.entity.User;
import com.overmind.overmind_chatbot.entity.enums.QuestionStatus;
import com.overmind.overmind_chatbot.repository.AnswerRepository;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import com.overmind.overmind_chatbot.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final UsersRepository usersRepository;
    private final AnswerRepository answerRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository, UsersRepository usersRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.usersRepository = usersRepository;
        this.answerRepository = answerRepository;
    }


    @Transactional
    public void updateQuestion(Long recordId, String newQuestion, String newAnswer) {
        // ID로 질문 찾기
        Question question = questionRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found for the given ID"));

        // 질문 내용 업데이트
        question.setContent(newQuestion);
        questionRepository.save(question);

        // 해당 질문과 연결된 답변 찾기
        Answer answer = answerRepository.findByQuestion_Id(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Answer not found for the given Question ID"));

        // 답변 내용 업데이트
        answer.setContent(newAnswer);
        answerRepository.save(answer);
    }

    @Transactional
    public void deleteQuestion(Long recordId) {
        // ID로 질문 찾기
        Question question = questionRepository.findById(recordId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found for the given ID"));

        // 해당 질문과 연결된 답변 찾기
        Answer answer = answerRepository.findByQuestion_Id(recordId)
                .orElse(null); // 답변이 없을 수 있으므로 Optional 처리

        // 답변이 존재할 경우 삭제
        if (answer != null) {
            answerRepository.delete(answer);
        }

        // 질문 삭제
        questionRepository.delete(question);
    }


    // 질문 저장하기
    @Transactional
    public Question saveQuestion(String content, User userId) {
        Question question = new Question();
        question.setContent(content);
        question.setStatus(QuestionStatus.QUESTION_REGISTERED);
        question.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        question.setUser(userId); // 조회된 user를 설정

        return questionRepository.save(question);
    }

    // 질문 상태 바꾸기
    @Transactional
    public void updateQuestionStatus(Long questionId, QuestionStatus status) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found"));

        question.setStatus(status);
        questionRepository.save(question); // 변경된 상태 저장
    }
    public List<QuestionRecordDTO> getUserRecords(Long userId) {
        List<Question> questions = questionRepository.findByUser_Id(userId);
        if (questions.isEmpty()) {
            return Collections.emptyList();
        }

        // 질문 목록을 DTO 형태로 변환
        return questions.stream().map(question -> {
            // 해당 질문에 대한 답변 찾기
            Answer answer = answerRepository.findByQuestion(question).stream()
                    .findFirst()
                    .orElse(null);

            return new QuestionRecordDTO(
                    question.getId(),
                    question.getContent(),
                    answer != null ? answer.getContent() : "No answer",
                    answer != null ? answer.getCreatedAt() : null,
                    question.getUser().getUserId(),
                    answer != null
            );
        }).collect(Collectors.toList());
    }
}

