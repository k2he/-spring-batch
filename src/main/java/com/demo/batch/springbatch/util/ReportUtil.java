package com.demo.batch.springbatch.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ReportUtil {

    private static final String DATE_SEPERATOR_BEGIN = "[";
    private static final String DATE_SEPERATOR_END = "]";
    private static final String EMPTY_STRING = "";

    /*
     * Input:
     * originalFileName = order_reporting_[yyyyMMdd].csv
     * datetime = 20210501
     * Output: order_reporting_20210501.csv
     *
     * * Input:
     * originalFileName = order_reporting_[yyyy-MM-dd].csv
     * datetime = 20210501
     * Output: order_reporting_2021-05-01.csv
     */
    public static String getFileName(String originalFileName, LocalDate date) {
        var dateBeginIndex = originalFileName.indexOf(DATE_SEPERATOR_BEGIN) + 1;
        var dateEndIndex = originalFileName.lastIndexOf(DATE_SEPERATOR_END);

        var dateFormatText = originalFileName.substring(dateBeginIndex, dateEndIndex);
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(dateFormatText);

        return originalFileName.replace(dateFormatText, date.format(dateFormatter))
                .replace(DATE_SEPERATOR_BEGIN, EMPTY_STRING)
                .replace(DATE_SEPERATOR_END, EMPTY_STRING);
    }
}
