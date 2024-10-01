package com.overmind.overmind_chatbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class OvermindChatbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(OvermindChatbotApplication.class, args);
	}

}
