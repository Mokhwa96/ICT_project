package com.overmind.overmind_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
@EnableJpaAuditing
public class OvermindChatbotApplication {
	public static void main(String[] args) {
		SpringApplication.run(OvermindChatbotApplication.class, args);
	}

}
