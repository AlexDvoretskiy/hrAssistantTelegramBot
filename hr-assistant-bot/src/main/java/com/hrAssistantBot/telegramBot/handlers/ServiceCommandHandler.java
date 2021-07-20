package com.hrAssistantBot.telegramBot.handlers;


import com.hrAssistantBot.telegramBot.commands.Command;
import com.hrAssistantBot.telegramBot.services.ExecutorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import persistence.services.CandidateService;
import persistence.services.VacancyService;


public class ServiceCommandHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(ServiceCommandHandler.class);

    public ServiceCommandHandler(VacancyService vacancyService, CandidateService candidateService) {
        super(vacancyService, candidateService);
    }

    @Override
    public void handleRequest(Command command, Message message) {
        switch (command) {
            case START:
                sendMenu(message.getChatId());
                registerUser(message.getChatId());
                break;
            case HELP:
                sendHelpMessage(message.getChatId());
                break;
        }
    }

    private void registerUser(Long chatId) {
        candidateService.createUserIfNotExists(chatId);
    }

    private void sendHelpMessage(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText("Бот поможет Вам узнать о компании и ознакомиться с актуальными вакансиями: просто выберите один из пунктов меню");
        ExecutorService.getInstance().sendMessage(sendMessage);
    }
}
