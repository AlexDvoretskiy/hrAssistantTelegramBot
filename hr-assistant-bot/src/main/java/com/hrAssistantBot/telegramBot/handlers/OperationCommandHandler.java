package com.hrAssistantBot.telegramBot.handlers;



import com.hrAssistantBot.telegramBot.commands.Command;
import com.hrAssistantBot.telegramBot.exceptions.UserDataParseException;
import com.hrAssistantBot.telegramBot.exceptions.UserDataValidateException;
import com.hrAssistantBot.telegramBot.parsers.UserDataParser;
import com.hrAssistantBot.telegramBot.services.ExecutorService;
import com.hrAssistantBot.telegramBot.services.FileService;
import com.hrAssistantBot.telegramBot.view.buttons.ChooseButton;
import com.hrAssistantBot.telegramBot.view.buttons.MenuButton;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;

import persistence.dto.*;
import persistence.enums.Stage;
import persistence.services.CandidateService;
import persistence.services.CompanyInfoService;
import persistence.services.VacancyService;

import java.util.Date;
import java.util.List;


public class OperationCommandHandler extends CommandHandler {
    private static final Logger log = LoggerFactory.getLogger(OperationCommandHandler.class);

    public OperationCommandHandler(VacancyService vacancyService, CandidateService candidateService) {
        super(vacancyService, candidateService);
    }

    @Override
    public void handleRequest(Command command, Message message) {
        CandidateDto candidateDto = candidateService.findByChatId(message.getChatId());

        switch (candidateDto.getStage()) {
            case REGISTERED:
                handleRegisterStage(message, candidateDto);
                break;
            case VACANCY_LIST:
                handleVacancyListStage(message, candidateDto);
                break;
            case VACANCY_CHOOSE:
                handleVacancyChooseStage(message, candidateDto);
                break;
            case FIO_FILLING:
                handleFioFilingStage(message, candidateDto);
                break;
            case BIRTHDATE_FILLING:
                handleBirthdateFillingStage(message, candidateDto);
                break;
            case PHONE_FILLING:
                handlePhoneFillingStage(message, candidateDto);
                break;
            case EMAIL_FILLING:
                handleEmailFillingStage(message, candidateDto);
                break;
            case CV_FILLING:
                handleCvFillingStage(message, candidateDto);
                break;
            case ASSIGNMENT_OFFERING:
                handleAssignmentOfferingStage(message, candidateDto);
                break;
            case ASSIGNMENT_FILLING:
                handleAssignmentFillingStage(message, candidateDto);
                break;
        }
    }


    private void handleRegisterStage(Message message, CandidateDto candidateDto) {
        String messageText = message.getText();

        if (messageText.equals(MenuButton.ABOUT_COMPANY.getTitle())) {
            sendInfoAboutCompany(message.getChatId());
        } else if (messageText.equals(MenuButton.VACANCIES.getTitle())) {
            sendAllActiveVacancies(message.getChatId());
            candidateDto.setStage(Stage.VACANCY_LIST);
        } else {
            sendMenuErrorMessage(message.getChatId());
        }
    }


    private void sendInfoAboutCompany(long chatId) {
        CompanyInfoDto companyInfoDto = CompanyInfoService.getInstance().findOneByActiveTrue();
        SendMessage sendMessage = getPreparedHtmlSendMessage(chatId, companyInfoDto.getHtmlDescription());

        ExecutorService.getInstance().sendMessage(sendMessage);
    }


    private void handleVacancyListStage(Message message, CandidateDto candidateDto) {
        String messageText = message.getText();

        VacancyDto vacancyDto = vacancyService.findOneByTitleAndActiveTrue(messageText);
        if (vacancyDto == null) {
            sendMenuErrorMessage(message.getChatId());
            return;
        }

        SendMessage sendMessage = getPreparedHtmlSendMessage(message.getChatId(), vacancyDto.getHtmlDescription());
        ExecutorService.getInstance().sendMessage(sendMessage);

        SendMessage keyboardMessage = getPreparedDoubleValueKeyboardMessage(message.getChatId(), "Ваc заинтересовала эта вакансия?");
        ExecutorService.getInstance().sendMessage(keyboardMessage);

        candidateDto.setCurrentVacancy(vacancyDto);
        candidateDto.setStage(Stage.VACANCY_CHOOSE);
    }


    private void handleVacancyChooseStage(Message message, CandidateDto candidateDto) {
        String messageText = message.getText();

        if (messageText.equals(ChooseButton.YES.getTitle())) {
            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Пожалуйста укажите Вашу Фамилию Имя Отчество");
            ExecutorService.getInstance().sendMessage(sendMessage);

            candidateDto.setStage(Stage.FIO_FILLING);
        } else if (messageText.equals(ChooseButton.NO.getTitle())) {
            sendAllActiveVacancies(message.getChatId());
            candidateDto.setStage(Stage.VACANCY_LIST);
        } else {
            sendMenuErrorMessage(message.getChatId());
        }
    }


    private void handleFioFilingStage(Message message, CandidateDto candidateDto) {
        try {
            String messageText = message.getText();
            List<String> userFio = UserDataParser.parseFio(messageText);

            if (userFio.size() == 2) {
                candidateDto.setLastName(userFio.get(0));
                candidateDto.setFirstName(userFio.get(1));
            } else {
                candidateDto.setLastName(userFio.get(0));
                candidateDto.setFirstName(userFio.get(1));

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < userFio.size(); i++) {
                    stringBuilder.append(userFio.get(i)).append(StringUtils.SPACE);
                }
                stringBuilder.deleteCharAt(stringBuilder.length() - 1);

                candidateDto.setMiddleName(stringBuilder.toString());
            }
            candidateDto.setStage(Stage.BIRTHDATE_FILLING);
            candidateService.update(candidateDto);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Пожалуйста укажите дату вашего рождения");
            ExecutorService.getInstance().sendMessage(sendMessage);
        } catch (UserDataParseException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }


    private void handleBirthdateFillingStage(Message message, CandidateDto candidateDto) {
        try {
            String messageText = message.getText();
            Date userBirthdate = UserDataParser.parseDate(messageText);

            candidateDto.setDateOfBirth(userBirthdate);
            candidateDto.setStage(Stage.PHONE_FILLING);

            candidateService.update(candidateDto);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Пожалуйста укажите Ваш номер телефона");
            ExecutorService.getInstance().sendMessage(sendMessage);
        } catch (UserDataParseException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }


    private void handlePhoneFillingStage(Message message, CandidateDto candidateDto) {
        try {
            String messageText = message.getText();
            String phoneNumber = UserDataParser.parsePhoneNumber(messageText);

            candidateDto.setPhone(phoneNumber);
            candidateDto.setStage(Stage.EMAIL_FILLING);

            candidateService.update(candidateDto);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Пожалуйста укажите Вашу электронную почту");
            ExecutorService.getInstance().sendMessage(sendMessage);
        } catch (UserDataParseException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }


    private void handleEmailFillingStage(Message message, CandidateDto candidateDto) {
        try {
            String messageText = message.getText();
            String email = UserDataParser.parseEmail(messageText);

            candidateDto.setEmail(email);
            candidateDto.setStage(Stage.CV_FILLING);

            candidateService.update(candidateDto);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Пожалуйста загрузите свое резюме в одном из форматов: pdf, doc");
            ExecutorService.getInstance().sendMessage(sendMessage);
        } catch (UserDataParseException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }


    private void handleCvFillingStage(Message message, CandidateDto candidateDto) {
        try {
            String fileName = FileService.getInstance().saveCvDocument(message.getDocument());
            candidateDto.setCvFileName(fileName);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Ваше резюме успешно сохранено");
            ExecutorService.getInstance().sendMessage(sendMessage);

            if (candidateDto.getCurrentVacancy().hasAssignment()) {
                final String messageText = "Данная вакансия предусматривает прохождение тестового задания. Вам будет направлено тестовое задание. Готовы выполнить его сейчас?";
                SendMessage keyboardMessage = getPreparedDoubleValueKeyboardMessage(message.getChatId(), messageText);
                ExecutorService.getInstance().sendMessage(keyboardMessage);
                candidateDto.setStage(Stage.ASSIGNMENT_OFFERING);
                candidateService.update(candidateDto);
            } else {
                sendAllActiveVacancies(message.getChatId());
                candidateDto.setStage(Stage.VACANCY_LIST);
                candidateService.updateAndRefresh(candidateDto);
            }
        } catch (UserDataValidateException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }


    private void handleAssignmentOfferingStage(Message message, CandidateDto candidateDto) {
        String messageText = message.getText();

        if (messageText.equals(ChooseButton.YES.getTitle())) {
            AssignmentDto assignmentDto = candidateDto.getCurrentVacancy().getAssignmentDto();

            String text = "После завершения прохождения тестового задания необходимо загрузить результат в чат в формате doc или pdf";
            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), text);
            ExecutorService.getInstance().sendMessage(sendMessage);

            InputFile inputFile = new InputFile();
            inputFile.setMedia(FileService.getInstance().getAssignmentFile(assignmentDto.getFileName()));

            SendDocument sendDocument = new SendDocument();
            sendDocument.setChatId(String.valueOf(message.getChatId()));
            sendDocument.setDocument(inputFile);

            ExecutorService.getInstance().sendDocument(sendDocument);

            candidateDto.setStage(Stage.ASSIGNMENT_FILLING);
            candidateService.update(candidateDto);
        } else if (messageText.equals(ChooseButton.NO.getTitle())) {
            sendAllActiveVacancies(message.getChatId());
            candidateDto.setStage(Stage.REGISTERED);
            candidateService.update(candidateDto);
        } else {
            sendMenuErrorMessage(message.getChatId());
        }
    }


    private void handleAssignmentFillingStage(Message message, CandidateDto candidateDto) {
        try {
            String fileName = FileService.getInstance().saveAssignmentFile(message.getDocument());
            AssignmentResultDto assignmentResultDto = new AssignmentResultDto(candidateDto.getId(), candidateDto.getCurrentVacancy(), fileName);

            candidateDto.addAssignmentResult(assignmentResultDto);
            candidateDto.setStage(Stage.REGISTERED);
            candidateService.updateAndRefresh(candidateDto);

            SendMessage sendMessage = getPreparedSendMessageWithKeyboardRemove(message.getChatId(), "Ваше тестовое задание успешно сохранено");
            ExecutorService.getInstance().sendMessage(sendMessage);

            sendAllActiveVacancies(message.getChatId());
        } catch (UserDataValidateException e) {
            sendErrorMessage(message.getChatId(), e.getMessage());
        }
    }
}
