package com.hrAssistantWeb.utils;


import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeUtils {
    private static final String DEFAULT_DATE_PATTER = "dd.MM.yyyy";

    public static String convertDateToString(Date date) {
        if (date == null)
            return StringUtils.EMPTY;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DEFAULT_DATE_PATTER);
        return simpleDateFormat.format(date);
    }
}
