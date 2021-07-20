package com.hrAssistantBot.telegramBot.view;


import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ViewBuilder {

    public static ReplyKeyboardMarkup buildVerticalReplyKeyboardMarkup(@NotNull List<String> titles) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getPreparedReplyKeyboardMarkup();

        List<KeyboardRow> keyboard = new ArrayList<>();
        for (String title : titles) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(title);
            keyboard.add(keyboardRow);
        }
        replyKeyboardMarkup.setKeyboard(keyboard);
        return replyKeyboardMarkup;
    }

    public static ReplyKeyboardMarkup buildDoubleValueReplyKeyboardMarkup(@NotNull String firstValue, @NotNull String secondValue) {
        ReplyKeyboardMarkup replyKeyboardMarkup = getPreparedReplyKeyboardMarkup();
        KeyboardRow keyboardRow = new KeyboardRow();

        KeyboardButton firstButton = new KeyboardButton(firstValue);
        KeyboardButton secondButton = new KeyboardButton(secondValue);

        keyboardRow.add(firstButton);
        keyboardRow.add(secondButton);

        replyKeyboardMarkup.setKeyboard(Collections.singletonList(keyboardRow));
        return replyKeyboardMarkup;
    }

    private static ReplyKeyboardMarkup getPreparedReplyKeyboardMarkup() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        return replyKeyboardMarkup;
    }
}
