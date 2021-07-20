package com.hrAssistantBot;


import com.hrAssistantBot.telegramBot.TelegramBot;
import com.hrAssistantBot.telegramBot.properties.BotConfiguration;
import com.hrAssistantBot.telegramBot.properties.ConfigurationParser;

import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.net.Authenticator;
import java.net.PasswordAuthentication;


public class BotApplication {
    private static final Logger log = LoggerFactory.getLogger(BotApplication.class);

    public static void main(String[] args) {
        try {
            log.info("Инициализация TelegramBotsApi");
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);

            log.info("Создание конфигурационных настроек для подключения");
            ConfigurationParser configurationParser = new ConfigurationParser();
            configurationParser.parseConfigurations();

            DefaultBotOptions botOptions = new DefaultBotOptions();
            TelegramBot bot = new TelegramBot(botOptions);

            log.info("Регистрация телеграм бота [{}]", BotConfiguration.getBotUsername());
            botsApi.registerBot(bot);
            log.info("Телеграм бот [{}] успешно запущен", BotConfiguration.getBotUsername());
        } catch (TelegramApiException e) {
            log.error("Ошибка при запуске телеграмм бота {}", BotConfiguration.getBotUsername(), e);
        }
    }
}
