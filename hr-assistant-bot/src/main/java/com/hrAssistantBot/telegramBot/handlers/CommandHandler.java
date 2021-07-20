package com.hrAssistantBot.telegramBot.handlers;


import com.hrAssistantBot.telegramBot.commands.Command;
import com.hrAssistantBot.telegramBot.services.ExecutorService;
import com.hrAssistantBot.telegramBot.view.buttons.ChooseButton;
import com.hrAssistantBot.telegramBot.view.buttons.MenuButton;
import com.hrAssistantBot.telegramBot.view.ViewBuilder;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import persistence.dto.VacancyDto;
import persistence.services.CandidateService;
import persistence.services.VacancyService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public abstract class CommandHandler {
    protected final VacancyService vacancyService;
    protected final CandidateService candidateService;

    protected CommandHandler(VacancyService vacancyService, CandidateService candidateService) {
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
    }

    public abstract void handleRequest(Command command, Message message);

    protected void sendMenu(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        List<String> menuButtonTitles = Arrays.stream(MenuButton.values())
                .map(MenuButton::getTitle)
                .collect(Collectors.toList());

        ReplyKeyboardMarkup replyKeyboardMarkup = ViewBuilder.buildVerticalReplyKeyboardMarkup(menuButtonTitles);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText("Выберете один из пунктов меню");

        ExecutorService.getInstance().sendMessage(sendMessage);
    }

    protected void sendAllActiveVacancies(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));

        List<String> vacancyTitleList = vacancyService.findAllByActiveTrue()
                .stream().map(VacancyDto::getTitle)
                .collect(Collectors.toList());

        ReplyKeyboardMarkup replyKeyboardMarkup = ViewBuilder.buildVerticalReplyKeyboardMarkup(vacancyTitleList);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        sendMessage.setText("Какая позиция Вам наиболее интересна?");

        ExecutorService.getInstance().sendMessage(sendMessage);
    }

    protected void sendMenuErrorMessage(long chatId) {
        SendMessage sendMessage = getPreparedSendMessage(chatId, "Пожалуйста, выберите один из пунктов меню");
        ExecutorService.getInstance().sendMessage(sendMessage);
    }

    protected void sendErrorMessage(long chatId, String message) {
        SendMessage sendMessage = getPreparedSendMessage(chatId, message);
        ExecutorService.getInstance().sendMessage(sendMessage);
    }

    protected SendMessage getPreparedSendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);
        return sendMessage;
    }

    protected SendMessage getPreparedHtmlSendMessage(long chatId, String text) {
        SendMessage sendMessage = getPreparedSendMessage(chatId, text);
        sendMessage.setParseMode(ParseMode.HTML);
        return sendMessage;
    }

    protected SendMessage getPreparedSendMessageWithKeyboardRemove(long chatId, String text) {
        SendMessage sendMessage = getPreparedSendMessage(chatId, text);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        return sendMessage;
    }

    protected SendMessage getPreparedDoubleValueKeyboardMessage(long chatId, String text) {
        SendMessage keyboardMessage = new SendMessage();
        ReplyKeyboardMarkup replyKeyboardMarkup = ViewBuilder.buildDoubleValueReplyKeyboardMarkup(ChooseButton.YES.getTitle(), ChooseButton.NO.getTitle());
        keyboardMessage.setChatId(String.valueOf(chatId));
        keyboardMessage.setReplyMarkup(replyKeyboardMarkup);
        keyboardMessage.setText(text);
        return keyboardMessage;
    }
}
