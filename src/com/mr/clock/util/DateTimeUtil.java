package com.mr.clock.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期时间工具类
 * 
 * @author mingrisoft
 *
 */

public class DateTimeUtil {

    /**
     * 获取当前时间
     * 
     * @return
     */
    public static String timeNow() {
        return new SimpleDateFormat("HH:mm:ss").format(new Date());
    }

    /**
     * 获取当前日期
     * 
     * @return
     */
    public static String dateNow() {
        return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    }

    /**
     * 获取当前日期和时间
     * 
     * @return
     */
    public static String dateTimeNow() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }

    /**
     * 获取由当前年、月、日、时、分、秒数字所组成的数组
     * 
     * @return
     */
    public static Integer[] now() {
        // 保存年、月、日、时、分、秒的数组
        Integer now[] = new Integer[6];
        Calendar c = Calendar.getInstance();// 日历对象
        now[0] = c.get(Calendar.YEAR);// 年
        now[1] = c.get(Calendar.MONTH) + 1;// 月
        now[2] = c.get(Calendar.DAY_OF_MONTH);// 日
        now[3] = c.get(Calendar.HOUR_OF_DAY);// 时
        now[4] = c.get(Calendar.MINUTE);// 分
        now[5] = c.get(Calendar.SECOND);// 秒
        return now;
    }

    /**
     * 获取指定月份的总天数
     * 
     * @param year  年
     * @param month 月
     * @return 天数
     */
    public static int getLastDay(int year, int month) {
        Calendar c = Calendar.getInstance();// 日历对象
        c.set(Calendar.YEAR, year);// 指定年
        c.set(Calendar.MONTH, month - 1);// 指定月
        // 返回这月的最后一天
        return c.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    /**
     * 以yyyy-MM-dd HH:mm:ss格式将字符串转化为Date对象
     * 
     * @param datetime 符合格式的字符串
     * @return
     * @throws ParseException 如果各字符串内容不能转为具体日期，则抛出该异常
     */
    public static Date dateOf(String datetime) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(datetime);
    }

    /**
     * 创建指定日期时间的Date对象
     * 
     * @param year  年
     * @param month 月
     * @param day   日
     * @param time  HH:mm:ss格式的时间字符串
     * @return
     * @throws ParseException
     */
    public static Date dateOf(int year, int month, int day, String time) throws ParseException {
        String datetime = String.format("%4d-%02d-%02d %s", year, month, day, time);
        return dateOf(datetime);
    }

    /**
     * 检查时间字符串是否符合HH:mm:ss格式
     * 
     * @param time 时间字符串
     * @return
     */
    public static boolean checkTimeStr(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        try {
            sdf.parse(time);// 将时间字符串转为Date对象
            return true;
        } catch (ParseException e) {
            return false;// 发生异常则表示字符串格式错误
        }
    }
}
