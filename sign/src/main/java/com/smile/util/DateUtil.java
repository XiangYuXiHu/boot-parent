package com.smile.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @Description
 * @ClassName DateUtil
 * @Author smile
 * @date 2023.03.25 12:03
 */
public class DateUtil {

    /**
     * format
     *
     * @param localDate
     * @param pattern
     * @return
     */
    public static String format(LocalDate localDate, String pattern) {
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }
}
