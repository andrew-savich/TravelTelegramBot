package com.andrewsavich.traveltelegrambot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	@Value("${telegram.token}")
	private String token;
	@Value("${telegram.username}")
	private String userName;
	private byte countUnknownMessages = 0;

	@Autowired
	CityService cityService;

	@Override
	public void onUpdateReceived(Update update) {
		Message message = update.getMessage();
		if (message != null && message.hasText()) {
			City city = cityService.getCityByTitle(message.getText());

			if (city != null) {
				sendMsg(message, city.getDescription());
				countUnknownMessages = 0;
			} else {
				switch (message.getText()) {
				case "/start":
					sendMsg(message, DefaultMessage.START + "\n" + cityService.allCityTitles());
					countUnknownMessages = 0;
					break;
				case "/help":
					sendMsg(message, DefaultMessage.HELP + "\n" + cityService.allCityTitles());
					countUnknownMessages = 0;
					break;
				default:
					if (countUnknownMessages < 2) {
						sendMsg(message, DefaultMessage.UNKNOWN);
						++countUnknownMessages;
					} else {
						sendMsg(message, DefaultMessage.HELP + "\n" + cityService.allCityTitles());
						countUnknownMessages = 0;
					}
					break;
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		return userName;
	}

	@Override
	public String getBotToken() {
		return token;
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
