package persistence.enums;


import lombok.Getter;
import lombok.Setter;


public enum Stage {
    REGISTERED("Пользователь успешно зарегистрирован"),
    VACANCY_LIST("Пользователь выбирает из списка вакансий"),
    VACANCY_CHOOSE("Пользователь заинтересовался вакансией"),
    FIO_FILLING("Пользователь заполняет ФИО"),
    BIRTHDATE_FILLING("Пользователь заполняет дату рождения"),
    PHONE_FILLING("Пользователь заполняет данные телефона"),
    EMAIL_FILLING("Пользователь заполняет данные электронной почты"),
    CV_FILLING("Пользователь загружает файл с резюме"),
    ASSIGNMENT_OFFERING("Предложение пользователю пройти тестовое задание, если оно есть"),
    ASSIGNMENT_FILLING("Пользователь загружает файл с тестовым заданием");


    @Getter
    @Setter
    private String description;

    Stage(String description) {
        this.description = description;
    }
}
