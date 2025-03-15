package com.tutort.assignments.Practice;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatting {
    public static void convert(String dateString) throws ParseException {
        System.out.println("Given date is " + dateString);

        DateFormat sdf = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
        Date date = sdf.parse(dateString);

        System.out.println("MM/dd/yyyy formatted date : " + new SimpleDateFormat("MM/dd/yyyy").format(date));
        System.out.println("yyyy-MM-dd formatted date : " + new SimpleDateFormat("yyyy-MM-dd").format(date));

    }

    public static void main(String[] args) {
        String utcDateStr = "Thu Apr 14 00:00:00 IST 2022";
        try {
            convert(utcDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
