package com.hrAssistantBot.telegramBot.view.buttons;

import lombok.Getter;
import lombok.Setter;

public enum MenuButton {
    ABOUT_COMPANY("О компании"),
    VACANCIES("Вакансии");

    @Getter
    @Setter
    private String title;

    MenuButton(String title) {
        this.title = title;
    }
}
