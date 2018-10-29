package com.itty.common.utils;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;

/**
 * @Auther: hezefei
 * @Date: 2018/9/8 12:06
 * @Description:
 */
public class DateUtils {
    private static Calendar calendar = Calendar.getInstance();

    /**
     * @return int
     * @Author hezefei
     * @Description 得到星期几
     * @Date 12:08 2018/9/8
     * @Param []
     **/
    public static int getTodayWeek() {

        calendar.setTime(new Date());
        int week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if (week < 0) week = 7;
        return week;
    }

    /**
     * @return int
     * @Author hezefei
     * @Description 计算时间差
     * @Date 12:14 2018/9/8
     * @Param [startDate, endDate]
     **/
    public static int getMunite(Date startDate, Date endDate) {

        long start = startDate.getTime();
        long end = endDate.getTime();
        int munite = (int) (end - start) / (1000 * 60);
        return munite;
    }

    /**
     * @return java.util.Date
     * @Author hezefei
     * @Description 获取当天时间
     * @Date 12:15 2018/9/8
     * @Param [hour, minute]
     **/
    public static Date getDate(int hour, int minute) {

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

}
