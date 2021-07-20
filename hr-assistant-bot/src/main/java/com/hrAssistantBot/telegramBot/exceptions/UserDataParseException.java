package com.hrAssistantBot.telegramBot.exceptions;


public class UserDataParseException extends Exception {

    public UserDataParseException(String message) {
        super(message);
    }

    public UserDataParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
