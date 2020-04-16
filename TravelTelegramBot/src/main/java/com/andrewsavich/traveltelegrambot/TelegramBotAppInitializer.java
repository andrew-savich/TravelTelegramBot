package com.andrewsavich.traveltelegrambot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class TelegramBotAppInitializer {
	
	static {
		ApiContextInitializer.init();
	}

	public static void main(String[] args) {
		
		SpringApplication.run(TelegramBotAppInitializer.class, args);
	}

}
