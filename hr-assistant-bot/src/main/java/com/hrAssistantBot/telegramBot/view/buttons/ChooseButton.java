package com.hrAssistantBot.telegramBot.view.buttons;

import lombok.Getter;
import lombok.Setter;

public enum ChooseButton {
    YES("Да"),
    NO("Нет");

    @Getter
    @Setter
    private String title;

    ChooseButton(String title) {
        this.title = title;
    }
}
