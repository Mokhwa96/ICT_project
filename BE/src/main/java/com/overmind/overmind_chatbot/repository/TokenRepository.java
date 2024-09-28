package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
}