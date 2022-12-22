package com.datn.hrm.application.utils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ApplicationUtils {

    public static String convertUnit(String unit) {

        switch (unit) {
            case "week":
                return "Tuần";
            case "month":
                return "Tháng";
            case "year":
                return "Năm";
            default:
                return "Ngày";
        }
    }

    public static void handleDate(Date fromDate, Date toDate, Date currentDate, String unit) {
    }
}
