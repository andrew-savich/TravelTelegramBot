package com.andrewsavich.traveltelegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.andrewsavich.traveltelegrambot.model.City;
import com.andrewsavich.traveltelegrambot.service.CityService;

@Component
public class Bot extends TelegramLongPollingBot {
	@Autowired
	CityService cityService;

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {
			City city = cityService.getCityByTitle(message.getText());

			if (city != null) {
				System.out.println(city);
				sendMsg(message, city.getDescription());
			} else {
				System.out.println("city is null");
			}
		}
	}

	@Override
	public String getBotUsername() {
		return "SavAndTravelBot";
	}

	@Override
	public String getBotToken() {
		return "1277036804:AAGHU3LRmSmsWXtMLaiaJhUCi-5qb8rm9RQ";
	}

	public void sendMsg(Message msg, String text) {
		SendMessage sendMessage = new SendMessage();
		sendMessage.enableMarkdown(true);
		sendMessage.setChatId(msg.getChatId().toString());
		sendMessage.setReplyToMessageId(msg.getMessageId());
		sendMessage.setText(text);

		try {

			execute(sendMessage);
		} catch (TelegramApiException e) {
			System.out.println("problem w/ sending message: " + e);
		}
	}

}
