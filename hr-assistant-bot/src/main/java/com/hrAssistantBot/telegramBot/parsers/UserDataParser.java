package com.hrAssistantBot.telegramBot.parsers;


import com.google.common.collect.ImmutableList;
import com.hrAssistantBot.telegramBot.exceptions.UserDataParseException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataParser {
    private static final String PHONE_NUMBER_PATTERN = "^((8|\\+7)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$";
    private static final String EMAIL_PATTERN = "\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*\\.\\w{2,4}";

    private static final List<String> DATE_PATTERNS = ImmutableList.of(
            "dd.MM.yyyy",
            "dd-MM-yyyy",
            "ddMMyyyy",
            "yyyy-MM-dd"
    );

    private static final String EMPTY_VALUE_EXCEPTION_MESSAGE = "Значение не может быть пустым, пожалуйста ведите корректное значение";


    public static List<String> parseFio(String value) throws UserDataParseException {
        if (StringUtils.isEmpty(value))
            throw new UserDataParseException(EMPTY_VALUE_EXCEPTION_MESSAGE);

        List<String> values = removeEmptyValuesIfExists(value.trim().split(StringUtils.SPACE));
        if (values.size() == 1) {
            throw new UserDataParseException("Пожалуйта, укажите Ваше полное имя");
        }
        return values;
    }


    public static Date parseDate(String value) throws UserDataParseException {
        if (StringUtils.isEmpty(value))
            throw new UserDataParseException(EMPTY_VALUE_EXCEPTION_MESSAGE);

        for (String datePattern : DATE_PATTERNS) {
            try {
                return new SimpleDateFormat(datePattern).parse(value);
            }
            catch (ParseException e) {
                //
            }
        }
        throw new UserDataParseException("Пожалуйста введите дату в одном из форматов: " + DATE_PATTERNS.toString());
    }


    public static String parsePhoneNumber(String value) throws UserDataParseException {
        if (StringUtils.isEmpty(value))
            throw new UserDataParseException(EMPTY_VALUE_EXCEPTION_MESSAGE);

        value = value.trim();
        Pattern pattern = Pattern.compile(PHONE_NUMBER_PATTERN);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            throw new UserDataParseException("Пожалуйста, введите номер в одном из форматов: +7-XXX-XXX-XX-XX, +7 XXX XXX XX XX, XXX-XXX-XX-XX, XXX XXX XX XX");
        }
        return value;
    }


    public static String parseEmail(String value) throws UserDataParseException {
        if (StringUtils.isEmpty(value))
            throw new UserDataParseException(EMPTY_VALUE_EXCEPTION_MESSAGE);

        value = value.trim();
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            throw new UserDataParseException("Пожалуйста, укажите корректный адрес электронной почты");
        }
        return value;
    }


    private static List<String> removeEmptyValuesIfExists(String[] strings) {
        List<String> result = new LinkedList<>();

        for (String value : strings) {
            if (StringUtils.isNotEmpty(value) && !StringUtils.isWhitespace(value))
                result.add(value);
        }
        return result;
    }
}
