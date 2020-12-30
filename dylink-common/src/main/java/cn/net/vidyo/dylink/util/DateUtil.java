package cn.net.vidyo.dylink.util;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by laochai on 2020/3/24.
 */
public class DateUtil extends org.apache.commons.lang3.time.DateUtils {
    public static Date convertStringToDate(String time) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
      //  format.setTimeZone(TimeZone.getTimeZone("CST+"));
        Date date = null;
        try {
            date = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    public static String getDateTimeToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//日期格式
       // sformat.setTimeZone(TimeZone.getTimeZone("CST+"));
        String tiem = sformat.format(date);

        return tiem;
    }
    public static String getDateToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        // sformat.setTimeZone(TimeZone.getTimeZone("CST+"));
        String tiem = sformat.format(date);

        return tiem;
    }
    public static String getTimeToString(Date date) {
        SimpleDateFormat sformat = new SimpleDateFormat("HH:mm:ss");//日期格式
        // sformat.setTimeZone(TimeZone.getTimeZone("CST+"));
        String tiem = sformat.format(date);

        return tiem;
    }
    public static String getBetweenDateTimeToString(Date beginDate,Date endDate) {
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");//日期格式
        SimpleDateFormat tformat = new SimpleDateFormat("HH:mm");//日期格式
        String beginDateString = dformat.format(beginDate);
        String endDateString = dformat.format(endDate);
        String beginTimeString = tformat.format(beginDate);
        String endTimeString = tformat.format(endDate);
        StringBuilder builder=new StringBuilder();
        builder.append(beginDateString);
        if(beginDateString.endsWith(endDateString)){
            builder.append(" ");
            builder.append(beginTimeString);
            builder.append("-");
            builder.append(endTimeString);
        }else{
            builder.append(" ");
            builder.append(beginTimeString);
            builder.append("-");
            builder.append(endDateString);
            builder.append(" ");
            builder.append(endTimeString);
        }
        return builder.toString();
    }

    /**
     * @param args
     * @throws ParseException
     */
    public static void main(String[] args) throws Exception {
//        Date date = new Date();
//        String aftertime = getAftertime(date, 3);
//        System.out.println(aftertime);
        System.out.println(DateUtil.getCurDateTime00());
        System.out.println(DateUtil.getCurDateTime24());
    }

    /**
     * 根据生日获取年龄
     *
     * @param birthDay
     * @return
     * @throws ParseException
     */
    public static Integer getAgeByBirth(Date birthDay) {
        Integer age = 0;
        if (null == birthDay) {
            return age;
        }
        try {
            Calendar cal = Calendar.getInstance();
            //出生日期晚于当前时间，无法计算
            Calendar bir = Calendar.getInstance();
            bir.setTime(birthDay);
            if (cal.before(bir)) {
                return age;
            }
            int yearNow = cal.get(Calendar.YEAR);
            int monthNow = cal.get(Calendar.MONTH);
            int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
            cal.setTime(birthDay);
            int yearBirth = cal.get(Calendar.YEAR);
            int monthBirth = cal.get(Calendar.MONTH);
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            age = yearNow - yearBirth;
            if (monthNow <= monthBirth) {
                if (monthNow == monthBirth) {
                    //当前日期在生日之前，年龄减一
                    if (dayOfMonthNow < dayOfMonthBirth) {
                        age--;
                    }
                } else {
                    age--;//当前月份在生日之前，年龄减一
                }
            }
        } catch (Exception e) {
            return age;
        }
        return age;
    }


    /**
     * 日期
     */
    public final static String DEFAILT_DATE_PATTERN = "yyyy-MM-dd";
    private final static SimpleDateFormat wkDateFm = new SimpleDateFormat("EEEE", Locale.CHINA);
    private final static SimpleDateFormat dateFm = new SimpleDateFormat("MM-dd");


    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM",
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd）
     */
    public static String getDate() {
        return getDate("yyyy-MM-dd");
    }

    /**
     * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String getDate(String pattern) {
        return DateFormatUtils.format(new Date(), pattern);
    }

    /**
     * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
     */
    public static String formatDate(Date date, Object... pattern) {
        String formatDate = null;
        if (pattern != null && pattern.length > 0) {
            formatDate = DateFormatUtils.format(date, pattern[0].toString());
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * 给date顺延几小时
     * @param date
     * @param hour
     * @return
     */
    public static String addDateMinut(Date date, int hour) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.HOUR, hour);// 24小时制
        date = cal.getTime();
        return format.format(date);

    }
    /**
     * 给date顺延几个月
     * @param date
     * @param month
     * @return
     */
    public static Date addDateMonth(Date date, int month) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null){
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, month);
        return cal.getTime();

    }
    /**
     * 给date顺延多少秒
     * @param date
     * @param sec
     * @return
     */
    public static String addDateSecond(Date date, Integer sec) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (date == null)
            return "";
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.SECOND, sec);// 24小时制
        date = cal.getTime();
        cal = null;
        return format.format(date);

    }

    /**
     * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String formatDateTime(Date date) {
        if (null == date) {
            return "";
        }
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * dateTime转换date，转换格式（yyyy-MM-dd）
     */
    public static String dateTime2Date(Date date) {
        if (null == date) {
            return "";
        }
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 判断车辆是否在线
     * @param time
     * @return
     */
    public static String isOnline(Date time) {
        if(null == time){
            return "0";
        }
        long loc = time.getTime();
        long now = System.currentTimeMillis();
        Long diff = 0L;
        if (now > loc) {
            diff = now - loc;
        } else {
            diff = 0L;
        }
        int seconds = (int) (diff / (1000 * 60));
        if (seconds > 10 || diff == 0L) {
            // 不在线
            return "0";
        } else {
            return "1";
        }
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getTime() {
        return formatDate(new Date(), "HH:mm:ss");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss:SSS）
     */
    public static String getTimeMillis() {
        return formatDate(new Date(), "HH:mm:ss:SSS");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getHourAndMin() {
        return formatDate(new Date(), "HH:mm");
    }

    /**
     * 得到当前时间字符串 格式（HH:mm:ss）
     */
    public static String getMothAndDay() {
        return formatDate(new Date(), "MM-dd");
    }

    /**
     * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
     */
    public static String getDateTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 得到当前年份字符串 格式（yyyy）
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 得到当前月份字符串 格式（MM）
     */
    public static String getMonth() {
        return formatDate(new Date(), "MM");
    }

    /**
     * 得到当天字符串 格式（dd）
     */
    public static String getDay() {
        return formatDate(new Date(), "dd");
    }

    /**
     * 得到当前星期字符串 格式（E）星期几
     */
    public static String getWeek() {
        return formatDate(new Date(), "E");
    }

    /**
     * 日期型字符串转化为日期 格式
     * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm",
     * "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
     * "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
     */
    public static Date parseDate(Object str) {
        if (str == null) {
            return null;
        }
        try {
            return parseDate(str.toString().trim(), parsePatterns);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取过去的天数
     *
     * @param date
     * @return
     */
    public static long pastDays(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (24 * 60 * 60 * 1000);
    }

    /**
     * 获取过去的小时
     *
     * @param date
     * @return
     */
    public static long pastHour(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 60 * 1000);
    }

    /**
     * 获取过去的分钟
     *
     * @param date
     * @return
     */
    public static long pastMinutes(Date date) {
        long t = System.currentTimeMillis() - date.getTime();
        return t / (60 * 1000);
    }

    /**
     * 转换为时间（天,时:分:秒.毫秒）
     *
     * @param timeMillis
     * @return
     */
    public static String formatDateTime(long timeMillis) {
        long day = timeMillis / (24 * 60 * 60 * 1000);
        long hour = (timeMillis / (60 * 60 * 1000) - day * 24);
        long min = ((timeMillis / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (timeMillis / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        long sss = (timeMillis - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000 - min * 60 * 1000 - s * 1000);
        return (day > 0 ? day + "," : "") + hour + ":" + min + ":" + s + "." + sss;
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(Date before, Date after) {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }

    /**
     * 获取两个日期之间的天数
     *
     * @param before
     * @param after
     * @return
     */
    public static double getDistanceOfTwoDate(String before, String after) {
        Date start = DateUtil.parseDate(before);
        Date end = DateUtil.parseDate(after);
        long beforeTime = start.getTime();
        long afterTime = end.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }


    /**
     * 增加年数
     *
     * @param optype
     * @param date
     * @param num
     * @return
     */
    public static String ADD_DATE(int optype, String date, int num) {
        String st_return = "";
        try {
            DateFormat daf_date = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.CHINA);
            daf_date.parse(date);
            Calendar calendar = daf_date.getCalendar();
            calendar.add(optype, num);
            String st_m = "";
            String st_d = "";
            int y = calendar.get(Calendar.YEAR);
            int m = calendar.get(Calendar.MONTH) + 1;
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            if (m <= 9) {
                st_m = "0" + m;
            } else {
                st_m = "" + m;
            }
            if (d <= 9) {
                st_d = "0" + d;
            } else {
                st_d = "" + d;
            }
            st_return = y + "-" + st_m + "-" + st_d;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return st_return;
    }

    /**
     * @return 返回时间格式类型字符串 YYYY-MM-DD
     */
    public static String getCurDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String strDate = sdf.format(date);
        return strDate;
    }

    /**
     * @return 返回时间格式类型字符串 YYYY-MM-DD 00:00:00
     */
    public static String getCurDateTime00() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
        String strDate = sdf.format(date);
        return strDate;
    }

    /**
     * @return 返回时间格式类型字符串 YYYY-MM-DD 23:59:59
     */
    public static String getCurDateTime24() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 23:59:59");
        String strDate = sdf.format(date);
        return strDate;
    }

    /**
     * 比较日期大小 *
     *
     * @param date1 String
     * @param date2 String
     * @return int
     */
    @SuppressWarnings("deprecation")
    public static int compareDate(String date1, String date2) {
        String[] date1Array = date1.split("-");
        String[] date2Array = date2.split("-");
        java.sql.Date date11 = new java.sql.Date(Integer.parseInt(
                date1Array[0], 10), Integer.parseInt(date1Array[1], 10),
                Integer.parseInt(date1Array[2], 10));
        java.sql.Date date22 = new java.sql.Date(Integer.parseInt(
                date2Array[0], 10), Integer.parseInt(date2Array[1], 10),
                Integer.parseInt(date2Array[2], 10));
        return date11.compareTo(date22);
    }

    /**
     * 加减分钟
     *
     * @param num
     * @param Date
     * @return
     */
    public static Date addMinute(int num, Date Date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Date);
        calendar.add(Calendar.MINUTE, num);// 把日期往后增加 num 天.整数往后推,负数往前移动
        return calendar.getTime(); // 这个时间就是日期往后推一天的结果
    }

    /**
     * 时间比较
     * <p>
     * 判断当前时间是否在时间time之前 <br/>
     * 时间格式 2005-4-21 16:16:34 <br/>
     * 添加人：
     *
     * @param time
     * @return
     */
    public static boolean isTimeBefore(String time) {
        if (time == null) {
            return false;
        }
        try {
            Date date1 = new Date();
            //DateFormat df = DateFormat.getDateTimeInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date d = df.parse(time);
            return date1.before(d);
        } catch (ParseException e) {
            return false;
        }
    }

    public static boolean isSystemDateBefore(String time) {
        if (time == null) {
            return false;
        }
        try {
            Date date1 = new Date();
            //DateFormat df = DateFormat.getDateTimeInstance();
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Date d = df.parse(time);
            return d.before(date1);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 判断司机证件是否过期
     *
     * @param day1 在前
     * @param day2 在后
     * @return 1:未过期;2:即将过期(30天);3:已过期
     */
    public static String isOutOneMouth(Date day1, Date day2) {
        if (day1 == null || day2 == null) {
            return "1";
        }
        if (day1.after(day2)) {
            return Math.abs(getDistanceOfTwoDate(day1, day2)) >= 30 ? "1" : "2";
        }
        if (day1.before(day2)) {
            return "3";
        }
        return "1";
    }


    /**
     * 判断时间是否过期
     *
     * @param day1 在前
     * @param day2 在后
     * @return 0:未过期 1已过期
     */
    public static String isEffective(Date day1, Date day2) {
        if (day1 == null || day2 == null) {
            return "1";
        }
        if (day1.after(day2)) {
            return "1";
        }
        if (day1.before(day2) || day1.equals(day2)) {
            return "0";
        }
        return "0";
    }

    /**
     * 比较时间date1是否在时间date2之前 时间格式 2008-11-25 16:30:10
     *
     * @param date1
     * @param date2
     * @return boolean; true:在date2之前
     * @author
     */
    public static boolean beforeDate(String date1, String date2) {
        try {
            Date d1 = convertString2Date(DEFAILT_DATE_PATTERN, date1);
            Date d2 = convertString2Date(DEFAILT_DATE_PATTERN, date2);
            return d1.before(d2);
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 转换日期字符串得到指定格式的日期类型
     *
     * @param formatString 需要转换的格式字符串
     * @param targetDate   需要转换的时间
     * @return
     * @throws ParseException
     */
    public static final Date convertString2Date(String formatString,
                                                String targetDate) {
        if (StringUtils.isBlank(targetDate))
            return null;
        SimpleDateFormat format = null;
        Date result = null;
        format = new SimpleDateFormat(formatString);
        try {
            result = format.parse(targetDate);
        } catch (ParseException pe) {
            //throw new ParseException(pe.getMessage(), pe.getErrorOffset());
            pe.printStackTrace();
        }
        return result;
    }

    /**
     * 转换日期,得到默认日期格式字符串
     *
     * @param targetDate
     * @return
     */
    public static String convertDate2String(Date targetDate) {
        return convertDate2String(DEFAILT_DATE_PATTERN, targetDate);
    }

    /**
     * 转换日期得到指定格式的日期字符串
     *
     * @param formatString 需要把目标日期格式化什么样子的格式。例如,yyyy-MM-dd HH:mm:ss
     * @param targetDate   目标日期
     * @return
     */
    public static String convertDate2String(String formatString, Date targetDate) {
        SimpleDateFormat format = null;
        String result = null;
        if (targetDate != null) {
            format = new SimpleDateFormat(formatString);
            result = format.format(targetDate);
        } else {
            return null;
        }
        return result;
    }

    /**
     * 返回系统当前日期时间的文本格式 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getSystemDateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }

    /**
     * 按天数获取date
     *
     * @param date
     * @param value
     * @return
     */
    public static Date addDay(Date date, int value) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, value);
        return now.getTime();
    }

    /**
     * 获取N前天的日期
     *
     * @param n
     * @return
     */
    public static String getStatetime(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, -n);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 指定日期加N天
     *
     * @param date n
     * @return
     */
    public static String getAftertime(Date date, int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, n);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }


    /**
     * 获取指定天数的N后天的日期
     *
     * @param n
     * @return
     */
    public static String getAftertime(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, n);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    /**
     * 获取N后天的日期
     *
     * @param n
     * @return
     */
    public static String getAfterDate(int n) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, n);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    public static String getTimeSubtractDay(String date, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        c.add(Calendar.DATE, -day);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }

    public static String addMonth(String date, int month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        c.add(Calendar.MONTH, month);
        Date monday = c.getTime();
        String preMonday = sdf.format(monday);
        return preMonday;
    }


    /**
     * 转换日期得到类微信消息时间格式
     *
     * @return
     */
    public static String transformDate(Date date) {
        String result = "";
        Date cDate = new Date();
        Calendar d1 = new GregorianCalendar();
        d1.setTime(date);
        Calendar d2 = new GregorianCalendar();
        d2.setTime(cDate);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        int y2 = d2.get(Calendar.YEAR);
        if (d1.get(Calendar.YEAR) != y2) {
            do {
                days += d1.getActualMaximum(Calendar.DAY_OF_YEAR);
                d1.add(Calendar.YEAR, 1);
            } while (d1.get(Calendar.YEAR) != y2);
        }
        if (days == 0) {
            result = "今天";
        } else if (days == 1) {
            result = "昨天";
        } else if (days > 1 && days <= 7) {
            result = wkDateFm.format(date);
        } else {
            result = dateFm.format(date);
        }
        return result;
    }

    /**
     * 获取当前日期的上一天
     *
     * @param date
     * @return
     */
    public static Date getProviousDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        date = calendar.getTime();
        return date;
    }

    public static String getDateStr(Long timeMIllis, Object... format) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMIllis);
        return DateUtil.formatDate(calendar.getTime(), format);
    }

    public static Date getDate(Long timeMIllis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMIllis);
        return calendar.getTime();
    }

    public static String getDayStr(String date, int day) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }
        calendar.add(Calendar.DAY_OF_YEAR, day);
        return formatDate(calendar.getTime(), "yyyy-MM-dd");
    }


    /**
     * 获取当前日期的上一天
     *
     * @param date
     * @param format
     * @return
     */
    public static String getProviousDay(Date date, String format) {
        Date d = getProviousDay(date);
        return formatDate(d, format);
    }


    /**
     * 将天数转换成几个月几天的格式
     * 每月默认取30天
     *
     * @return
     */
    public static String translateDateToStr(int liveDay) {
        String liveStr = "";
        int liveMonth = liveDay / 30;
        int liveDayNum = liveDay % 30;
        if (liveMonth != 0) {
            liveStr += liveMonth + "个月";
        }
        if (liveDayNum != 0) {
            liveStr += liveDayNum + "天";
        }
        return liveStr;
    }


    /**
     * 计算百度还款日
     *
     * @return
     */
    public static Integer translateBdRepayDay(String startDate, Integer prepaymentDay) {

        Date stDate = DateUtil.parseDate(startDate);
        if (prepaymentDay == 0) {
            prepaymentDay = 1;
        }
        Date prepayDate = DateUtil.addDay(stDate, -prepaymentDay);
        Integer resultDay = prepayDate.getDate();

        if (resultDay == 29 || resultDay == 30 || resultDay == 31) {
            resultDay = 28;
        }

        return resultDay;
    }

    /**
     * 获取指定时间的时间戳
     *
     * @param date
     * @param day
     * @return
     */
    public static Long getTimeInMillis(Date date, int day) {
        String dateStr = formatDate(date, "yyyy-MM-dd");
        date = parseDate(dateStr);
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.add(Calendar.DAY_OF_YEAR, day);
        return now.getTimeInMillis();
    }

    /**
     * 比较时间date1是否在时间date2之前 时间格式 2008-11-25 16:30:10
     *
     * @param date1
     * @param date2
     * @return boolean; true:在date2之前
     * @author
     */
    public static boolean beforeDateFormat(String date1, String date2) {
        try {
            Date d1 = convertString2Date("yyyy-MM-dd HH:mm:ss", date1);
            Date d2 = convertString2Date("yyyy-MM-dd HH:mm:ss", date2);
            return d1.before(d2);
        } catch (Exception e) {
            return true;
        }
    }

    /**
     * 两个时间相差多少小时(自动签发签收专用)
     *
     * @return
     */
    public static long hourBetweenTwoTime(String date1) throws ParseException {
        if (StringUtils.isEmpty(date1)) {
            return 0;
        }
        Date d1 = convertString2Date(date1);
        if (d1 == null) {
            return 0;
        }
        Date today = new Date();
        long hours = (today.getTime() - d1.getTime()) / (1000 * 60 * 60);
        return hours;
    }

    /**
     * 两个时间相差多少分钟
     *
     * @return
     */
    public static long minBetweenTwoTime(String date1) {
        if (StringUtils.isEmpty(date1)) {
            return 0;
        }
        Date d1 = convertString2Date("yyyy-MM-dd HH:mm:ss", date1);
        if (d1 == null) {
            return 0;
        }
        Date today = new Date();
        long hours = Math.abs((today.getTime() - d1.getTime()) / (1000 * 60));
        return hours;
    }

    public static boolean compTime(String s1, String s2) {
        try {
            if (s1.indexOf(":") < 0 || s1.indexOf(":") < 0) {
                System.out.println("格式不正确");
            } else {
                String[] array1 = s1.split(":");
                int total1 = Integer.valueOf(array1[0]) * 3600 + Integer.valueOf(array1[1]) * 60;
                String[] array2 = s2.split(":");
                int total2 = Integer.valueOf(array2[0]) * 3600 + Integer.valueOf(array2[1]) * 60;
                return total1 - total2 >= 0 ? true : false;
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            return true;
        }
        return false;
    }

    /**
     * 判断时间是不是今天
     *
     * @param str
     * @return 是返回true，不是返回false
     */
    public static boolean isNow(String str) {
        Date date = null;
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //当前时间
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        //获取今天的日期
        String nowDay = sf.format(now);
        //对比的时间
        String day = sf.format(date);
        return day.equals(nowDay);
    }

    /**
     * 比较时间date1是否在时间date2之前
     *
     * @return boolean; true:在date2之前
     * @author
     */
    public static boolean compareToDate(String date1, String date2) {
        try {
            Date dt1 = convertString2Date("yyyy-MM-dd HH:mm:ss", date1);
            Date dt2 = convertString2Date("yyyy-MM-dd HH:mm:ss", date2);
            return dt1.compareTo(dt2) < 0 ? true : false;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * @param beforeTime 时间1
     * @param afterTime  时间2
     * @param minute     比较分钟数
     * @return 时间差大于设定分钟数返回true
     * @desc 比较两时间差
     */
    public static boolean isTimeMornthanMinutes(String beforeTime, String afterTime, int minute) {
        if (beforeTime == null || afterTime == null) {
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date a = df.parse(afterTime);
            Date b = df.parse(beforeTime);
            return a.getTime() - b.getTime() - minute * 60 * 1000 > 0;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * @param beforeTime 时间1
     * @param afterTime  时间2
     * @return 时间2在时间1前返回true
     * @desc 两个时间比较,
     */
    public static boolean isTimeBefore(String beforeTime, String afterTime) {
        if (beforeTime == null || afterTime == null) {
            return false;
        }
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date a = df.parse(afterTime);
            Date b = df.parse(beforeTime);
            return b.before(a);
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * 两个时间相差多少分钟
     *
     * @return
     */
    public static long minBetweenTwoTime(String date1, String date2) throws ParseException {
        if (StringUtils.isEmpty(date1)) {
            return 0;
        }
        Date d1 = convertString2Date(date1);
        if (d1 == null) {
            return 0;
        }
        Date d2 = convertString2Date(date2);
        long hours = (d2.getTime() - d1.getTime()) / (1000 * 60);
        return hours;
    }

    /**
     * @param targetDate
     * @return
     * @throws ParseException
     */
    public static final Date convertString2Date(String targetDate) throws ParseException {
        if (StringUtils.isBlank(targetDate))
            return null;
        Date result = null;
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            result = df.parse(targetDate);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }
        return result;
    }

    /**
     * @param start 开始日期 yyyy-MM-dd
     * @param end   结束日期 yyyy-MM-dd
     * @return 日期列表
     * @desc 获取2个日期段中的所有日期
     */
    public static List<String> getBetweenDates(String start, String end) {
        List<String> result = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start_date = sdf.parse(start);
            Date end_date = sdf.parse(end);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start_date);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end_date);
            while (tempStart.before(tempEnd) || tempStart.equals(tempEnd)) {
                result.add(sdf.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // Collections.reverse(result);
        return result;
    }


    /**
     * 判断时间是否在时间段内
     *
     * @param nowTime   当前时间，yyyy-mm-dd
     * @param beginTime 开始时间，yyyy-mm-dd
     * @param endTime   结束时间，yyyy-mm-dd
     * @return true在内，false不在内
     */
    public static boolean belongTimeRange(Date nowTime, Date beginTime, Date endTime) {
        Calendar date = Calendar.getInstance();
        date.setTime(nowTime);
        Calendar begin = Calendar.getInstance();
        begin.setTime(beginTime);
        Calendar end = Calendar.getInstance();
        end.setTime(endTime);
        if (date.after(begin) && date.before(end)) {
            return true;
        } else if (nowTime.compareTo(beginTime) == 0 || nowTime.compareTo(endTime) == 0) {
            return true;
        } else {
            return false;
        }
    }

    /*
    得到两个日期相差多少小时
     */
    public static BigDecimal getHourOfTwoDay(String endDtStr, String startDtStr) {
        Date endDt = parseDate(endDtStr);
        Date startDt = parseDate(startDtStr);
        long t = endDt.getTime() - startDt.getTime();
        BigDecimal b = new BigDecimal(t);
        BigDecimal b1 = new BigDecimal(60 * 60 * 1000);
        return b.divide(b1, 2, BigDecimal.ROUND_HALF_UP);
    }

    public static List<String> getDays(String startTime, String endTime) {
        // 返回的日期集合
        List<String> days = new ArrayList<String>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);
            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);
            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return days;
    }

    /**
     * 生成当前时间戳
     * @return
     */
    public static long getNowTimeStamp(){
        return System.currentTimeMillis();
    }

}
