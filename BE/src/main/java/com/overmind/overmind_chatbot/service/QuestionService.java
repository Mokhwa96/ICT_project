package com.overmind.overmind_chatbot.service;
import com.overmind.overmind_chatbot.entity.Question;
import com.overmind.overmind_chatbot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    @Autowired
    QuestionRepository questionRepository;

    public Question createQuestion(Question question) {
        return questionRepository.save(question);
    }
}
