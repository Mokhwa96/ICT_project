package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUserIdAndPassword(String uid, String password);
    Optional<User> findByUserId(String userId);
}