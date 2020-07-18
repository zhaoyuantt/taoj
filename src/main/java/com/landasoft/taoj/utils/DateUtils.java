package com.landasoft.taoj.utils;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDateToString(Date date){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String sDate = simpleDateFormat.format(date);
        return sDate;
    }

    /**
     * 获取当前小时数
     * @return
     */
    public static int getDayHour(){
        DateTime dateTime = new DateTime();
        int hour = dateTime.getHourOfDay();
        return hour;
    }

    /**
     * 得到每周的第几天
     * @return
     */
    public static int getWeekDay(){
        DateTime dateTime = new DateTime();
        int day = dateTime.getDayOfWeek();
        return day;
    }

    public static void main(String[] args) {
        DateUtils.getDayHour();
        DateUtils.getWeekDay();
    }
}
