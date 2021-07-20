package com.hrAssistantBot.telegramBot;


import com.hrAssistantBot.telegramBot.commands.Command;
import com.hrAssistantBot.telegramBot.commands.CommandParser;
import com.hrAssistantBot.telegramBot.handlers.CommandHandler;
import com.hrAssistantBot.telegramBot.handlers.CommandHandlerFactory;
import com.hrAssistantBot.telegramBot.properties.BotConfiguration;
import com.hrAssistantBot.telegramBot.services.ExecutorService;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


public class TelegramBot extends TelegramLongPollingBot {

    public TelegramBot(DefaultBotOptions defaultBotOptions) {
        super(defaultBotOptions);
        ExecutorService.createInstance(this);
    }

    @Override
    public String getBotUsername() {
        return BotConfiguration.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return BotConfiguration.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            Command command = CommandParser.parseRequest(message.getText());

            CommandHandler commandHandler = CommandHandlerFactory.getHandler(command);
            commandHandler.handleRequest(command, message);
        }
    }

    @Override
    public void onClosing() {
        super.onClosing();
    }
}
