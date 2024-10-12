package com.overmind.overmind_chatbot.service;

import java.util.stream.Collectors;
import com.overmind.overmind_chatbot.dto.AnswerDTO;
import com.overmind.overmind_chatbot.entity.Answer;
import com.overmind.overmind_chatbot.repository.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class AnswerService {
    @Autowired
    private AnswerRepository answerRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<Answer> getAnswersByQuestionId(Long questionId) {
        return answerRepository.findByQuestionId(questionId);
    }

    public Optional<Answer> getAnswerById(Long id) {
        return answerRepository.findById(id);
    }

    public Answer createAnswer(Answer answer) {
        answer.setCreatedAt(LocalDateTime.now());
        return answerRepository.save(answer);
    }

    public Optional<Answer> updateAnswer(Long id, String content) {
        return answerRepository.findById(id).map(answer -> {
            answer.setContent(content);
            answer.setUpdatedAt(LocalDateTime.now());
            return answerRepository.save(answer);
        });
    }

    public void deleteAnswer(Long id) {
        answerRepository.deleteById(id);
    }


}
