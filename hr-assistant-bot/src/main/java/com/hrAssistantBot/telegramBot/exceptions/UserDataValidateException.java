package com.hrAssistantBot.telegramBot.exceptions;


public class UserDataValidateException extends Exception {

    public UserDataValidateException(String message) {
        super(message);
    }

    public UserDataValidateException(String message, Throwable cause) {
        super(message, cause);
    }
}
