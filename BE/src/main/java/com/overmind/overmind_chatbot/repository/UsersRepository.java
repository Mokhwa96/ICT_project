package com.overmind.overmind_chatbot.repository;

import com.overmind.overmind_chatbot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<User, Long> {
    User findByUidAndPassword(String uid, String password);
}