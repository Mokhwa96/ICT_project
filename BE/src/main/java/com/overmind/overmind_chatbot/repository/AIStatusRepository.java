package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.AiStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AIStatusRepository extends JpaRepository<AiStatus, Long> {
}