package com.example.demo.util;

import com.example.demo.bizEnum.DateFormatEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description 日期工具类，继承common.lang3的DateUtils
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2022/1/30 18:05
 */
@Slf4j
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
    public static final Long MILL_OF_DAY = 86400000L;  // 一天的毫秒值
    /**
     * 自定的长期
     */
    public static final String LONG_TERM = "2999-12-31";
    public static final String LONG_TERM_STR = "长期";

    /**
     * 秒数
     */
    private static final long SECOND_MINUTE = 60L;
    private static final long SECOND_HOUR = 60L * 60L;
    private static final long SECOND_DAY = 60L * 60L * 24L;
    private static final long SECOND_MONTH = 60L * 60L * 24L * 30L;
    private static final long SECOND_YEAR = 60L * 60L * 24L * 30L * 12L;
    /**
     * 中文转换数组
     */
    private static final char[] cnNum = new char[]{'零', '壹', '贰', '叁', '肆', '伍', '陆', '柒', '捌', '玖'};

    /**
     * @Description 格式化时间字符串，按指定格式
     * @Param date 需要转换的时间对象
     * @Param format 需要转换的目标格式
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:07
     */
    public static String toDateString(Date date, DateFormatEnum format) {
        if (date == null) {
            return null;
        }
        if (format == null) {
            throw new IllegalArgumentException();
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format.getDesc());
        return localDateTime.format(formatter);
    }

    /**
     * 获取指定格式的当前时间
     *
     * @Param format
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-05-31 11:06:50
     */
    public static String today(DateFormatEnum format) {
        return today(format.getDesc());
    }

    /**
     * 获取指定格式的当前时间
     *
     * @Param format
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-05-31 11:06:39
     */
    public static String today(String format) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.format(DateTimeFormatter.ofPattern(format));
    }

    /**
     * @Description 获取当前时间，格式为yyyyMMdd
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:22
     */
    public static String today8() {
        return today(DateFormatEnum.YMD8);
    }

    /**
     * @Description 获取当前时间，格式为yyyy-MM-dd
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:22
     */
    public static String today10() {
        return today(DateFormatEnum.YMD10);
    }

    public static String todayStart() {
        return today10() + " 00:00:00";
    }

    public static String todayEnd() {
        return today10() + " 23:59:59";
    }

    /**
     * @Description 获取当前时间，格式为yyyyMMddHHmmss
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:22
     */
    public static String today14() {
        return today(DateFormatEnum.YMDHMS14);
    }

    /**
     * @Description 获取当前时间，格式为yyyy-MM-dd HH:mm:ss
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:23
     */
    public static String today19() {
        return today(DateFormatEnum.YMDHMS19);
    }

    /**
     * @Description 获取昨日时间，格式为yyyy-MM-dd
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:24
     */
    public static String yesterday10() {
        DateFormat dateFormat = new SimpleDateFormat(DateFormatEnum.YMD10.getDesc());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        return dateFormat.format(calendar.getTime());
    }

    public static String yesterdayDate(String format) {
        DateFormat dateFormat = new SimpleDateFormat(format);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, -24);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * @Description 获取前n小时时间
     * @Param hourNum 前hourNum小时，大于0的证书
     * @Param format 返回时间格式
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:29
     */
    public static String hourAgo(int hourNum, DateFormatEnum format) {
        if (hourNum <= 0) {
            throw new IllegalArgumentException("必须大于0");
        }
        DateFormat dateFormat = new SimpleDateFormat(format.getDesc());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hourNum);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * @Description 获取前n秒时间
     * @Param secondNum
     * @Param format
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:30
     */
    public static String secondsAgo(int secondNum, DateFormatEnum format) {
        if (secondNum <= 0) {
            throw new IllegalArgumentException("必须大于0");
        }
        DateFormat dateFormat = new SimpleDateFormat(format.getDesc());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - secondNum);
        return dateFormat.format(calendar.getTime());
    }

    /**
     * @Description 返回date是否为周末
     * @Param date 待检查的日期
     * @Return boolean 是否为周末
     * @Author xubincheng
     * @Date 2022/1/30 20:31
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    public static boolean isWeekend(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(DateUtils.parseDate(date)));
        if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * Description: 是否为周日
     * Author: DB-SW
     * Date: 2023-11-07 10:49:11
     */
    public static boolean isSunday(String date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(Objects.requireNonNull(DateUtils.parseDate(date)));
        return calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }

    /**
     * @Description 获取当年，格式为yyyy
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:32
     */
    public static String todayYear() {
        return today(DateFormatEnum.Y);
    }

    /**
     * @Description 获取当月，格式为MM
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:32
     */
    public static String todayMonth() {
        return today(DateFormatEnum.M);
    }

    /**
     * @Description 获取当月，格式为yyyy-MM
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:32
     */
    public static String todayYM() {
        return today(DateFormatEnum.YM);
    }

    /**
     * @Description 获取当日，格式为dd
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:32
     */
    public static String todayDay() {
        return today(DateFormatEnum.D);
    }

    /**
     * @Description 获取当前小时，格式为dd
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:32
     */
    public static String todayHour() {
        return today(DateFormatEnum.H);
    }

    /**
     * @Description 获取两个日期的天数差，返回差的绝对值
     * @Param date1
     * @Param date2
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:33
     */
    public static long diffDays(String date1, String date2) {
        LocalDateTime dt1 = LocalDateTime.parse(date1, patternFormat(date1));
        LocalDateTime dt2 = LocalDateTime.parse(date2, patternFormat(date2));
        Duration duration = Duration.between(dt1, dt2);
        return Math.abs(duration.toDays());
    }

    /**
     * @Description 获取两个日期的天数差，返回差的绝对值
     * @Param date1
     * @Param date2
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:33
     */
    public static long diffDays(String date1, String date2, DateFormatEnum formatEnum) {
        LocalDateTime dt1 = LocalDateTime.parse(date1, DateTimeFormatter.ofPattern(formatEnum.getDesc()));
        LocalDateTime dt2 = LocalDateTime.parse(date2, DateTimeFormatter.ofPattern(formatEnum.getDesc()));
        Duration duration = Duration.between(dt1, dt2);
        return Math.abs(duration.toDays());
    }

    /**
     * @Description 获取两个日期的天数差，返回差的绝对值
     * @Param date1
     * @Param date2
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:33
     */
    public static Long diffDays(Date date1, Date date2) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant1 = date1.toInstant();
        Instant instant2 = date2.toInstant();

        LocalDate d1 = instant1.atZone(zoneId).toLocalDate();
        LocalDate d2 = instant2.atZone(zoneId).toLocalDate();
        return Math.abs(d1.toEpochDay() - d2.toEpochDay());
    }

    /**
     * @Description 获取两个时间的秒数差，返回差的绝对值
     * @Param time1
     * @Param time2
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:34
     */
    public static long diffSeconds(String time1, String time2) {
        LocalDateTime dt1 = LocalDateTime.parse(time1, patternFormat(time1));
        LocalDateTime dt2 = LocalDateTime.parse(time2, patternFormat(time2));
        Duration duration = Duration.between(dt1, dt2);
        return Math.abs(duration.toMillis() / 1000);
    }

    /**
     * @Description 获取两个时间的秒数差，返回差的绝对值
     * @Param date1
     * @Param date2
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:35
     */
    public static long diffSeconds(Date date1, Date date2) {
        ZoneId zoneId = ZoneId.systemDefault();
        Instant instant1 = date1.toInstant();
        Instant instant2 = date2.toInstant();
        LocalDateTime localDateTime1 = instant1.atZone(zoneId).toLocalDateTime();
        LocalDateTime localDateTime2 = instant2.atZone(zoneId).toLocalDateTime();
        Duration duration = Duration.between(localDateTime1, localDateTime2);
        return Math.abs(duration.toMillis() / 1000);
    }

    /**
     * @Description 时间字符串转换为Date，入参为空时返回null，根据入参字符串长度匹配时间格式
     * @Param dateStr 待转换的日期字符串
     * @Return java.util.Date
     * @Author xubincheng
     * @Date 2022/1/30 20:35
     */
    public static Date parseDate(String dateStr) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        return Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @Description 时间字符串转换为Date，入参为空时返回null
     * @Param dateStr
     * @Param format
     * @Return java.util.Date
     * @Author xubincheng
     * @Date 2022/1/30 20:36
     */
    public static Date parseDate(String dateStr, DateFormatEnum format) {
        if (StringUtils.isBlank(dateStr)) {
            return null;
        }
        DateTimeFormatter formatter = getDefaultDateTimeFormatter(format.getDesc());
        LocalDateTime dt = LocalDateTime.parse(dateStr, formatter);
        return Date.from(dt.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * @Description 获取当前秒级时间戳
     * @Param
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:39
     */
    public static long timestamp() {
        Instant instant = Instant.now();
        return instant.toEpochMilli();
    }

    public static long timestamp(String date) {
        LocalDateTime dt = LocalDateTime.parse(date, patternFormat(date));
        return dt.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
    }

    /**
     * @Description 获取当前秒级时间戳字符串
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:40
     */
    public static String timestampStr() {
        Instant instant = Instant.now();
        long currentSecond = instant.getEpochSecond();
        return String.valueOf(currentSecond);
    }

    /**
     * @Description 转换时间字符串的格式
     * @Param dateTime
     * @Param newFormat
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:40
     */
    public static String parseFormat(String dateTime, DateFormatEnum newFormat) {
        if (StringUtils.isBlank(dateTime)) {
            return "";
        }
        // 2023年9月14日，这个日期格式类型获取有瑕疵，例如 2023/9/14 和 2023-09-14无法区分
//        String patternByLength = getPatternByLength(dateTime);
//        if (StringUtils.isBlank(patternByLength)) {
//            return "";
//        }
//        DateTimeFormatter oldFormatter = getDefaultDateTimeFormatter(patternByLength);
        DateTimeFormatter oldFormatter = getDefaultDateTimeFormatter(getDateFormat(dateTime));
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(newFormat.getDesc());
        return LocalDateTime.parse(dateTime, oldFormatter).format(newFormatter);
    }

    public static String parseFormat(String dateTime, DateFormatEnum origFormat, DateFormatEnum newFormat) {
        if (StringUtils.isBlank(dateTime)) {
            return "";
        }
        DateTimeFormatter oldFormatter = DateTimeFormatter.ofPattern(origFormat.getDesc());
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern(newFormat.getDesc());
        return LocalDateTime.parse(dateTime, oldFormatter).format(newFormatter);
    }

    public static String ocrDateFormat(String dateStr) {
        try {
            //中文日期处理
            if (dateStr.contains("贰")) {
                dateStr = chineseDateConversion(dateStr);
            }
            //统一输出格式：yyyy-MM-dd
            return stringDate2FormatStr(dateStr, DateFormatEnum.YMD10.getDesc());
        } catch (Exception e) {
            log.error("ocr识别日期处理异常：{}", dateStr, e);
            return "";
        }
    }

    public static String chineseDateConversion(String dateStr) {
        if (org.apache.commons.lang3.StringUtils.isBlank(dateStr)) {
            return null;
        }
        StringBuilder stringBuilder;
        String a = dateStr.replace("年", "|")
                .replace("月", "|")
                .replace("日", "|");

        String[] b = a.split("\\|");
        //年
        String y = b[0].replace("零", "0");
        //月
        String m = b[1].length() == 1 ? String.format("%s%s", "0", b[1])
                ///零X  ||  壹拾
                : b[1].length() == 2 ? b[1].replace("零", "0").replace("壹拾", "10")
                //壹拾X
                : b[1].length() == 3 && !b[1].contains("零") ? b[1].replace("拾", "")
                //零壹拾 || 零XX
                : b[1].length() == 3 && b[1].contains("零") ? b[1].replace("零", "").replace("壹拾", "10")
                //零壹拾X
                : b[1].replace("零", "").replace("拾", "");
        //日
        String d = b[2].length() == 1 ? String.format("%s%s", "0", b[2])
                //零X  ||  X拾
                : b[2].length() == 2 ? b[2].replace("零", "0").replace("拾", "0")
                //零XX || 零X拾
                : b[2].length() == 3 && b[2].contains("零") ? b[2].replace("零", "").replace("拾", "0")
                //X拾X
                : b[2].length() == 3 && !b[2].contains("零") ? b[2].replace("拾", "")
                //零X拾X
                : b[2].replace("零", "").replace("拾", "");
        String dateUnit = String.format("%s%s%s", y, m, d);
        stringBuilder = chinese2Num(dateUnit);
        return stringBuilder == null ? "" : stringBuilder.toString();
    }

    /**
     * 中文字符转数字
     *
     * @param dateUnit 中文字符
     * @return 数字
     */
    public static StringBuilder chinese2Num(String dateUnit) {
        if (org.apache.commons.lang3.StringUtils.isBlank(dateUnit)) {
            return null;
        }
        StringBuilder dateStr = new StringBuilder();
        for (int i = 0; i < dateUnit.length(); i++) {
            //去当前遍历的中文字符
            boolean isExists = false;
            char c = dateUnit.charAt(i);
            for (int j = 0; j < cnNum.length; j++) {
                if (c == cnNum[j]) {
                    // 数字值 = 索引
                    dateStr.append(j);
                    isExists = true;
                    break;
                }
            }
            if (!isExists) {
                dateStr.append(c);
            }
        }
        return dateStr;
    }

    /**
     * 日期字符串格式化输出
     *
     * @param date
     * @param format
     * @return
     */
    public static String stringDate2FormatStr(String date, String format) {
        if (org.apache.commons.lang3.StringUtils.isBlank(date)) {
            return null;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(format)) {
            format = DateFormatEnum.YMD10.getDesc();
        }
        try {
            String dateOtherFormat = date.contains("/") ? "yyyy/MM/dd" : DateFormatEnum.YMD8.getDesc();
            String currentFormat = date.contains("-") ? DateFormatEnum.YMD10.getDesc() : dateOtherFormat;
            return date2String(string2Date(date, currentFormat), format);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转换 date-2-string
     *
     * @param date   日期
     * @param format 转换格式
     * @return 转换结果
     */
    public static String date2String(Date date, String format) {
        if (date == null) {
            return null;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(format)) {
            format = DateFormatEnum.YMD10.getDesc();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 日期转换 string-2-date
     *
     * @param date   日期
     * @param format 转换格式
     * @return 转换结果
     */
    public static Date string2Date(String date, String format) {
        if (org.apache.commons.lang3.StringUtils.isBlank(date)) {
            return null;
        }
        if (org.apache.commons.lang3.StringUtils.isBlank(format)) {
            format = "yyyy-MM-dd";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            return sdf.parse(date);
        } catch (ParseException e) {
            return null;
        }
    }


    /**
     * @Description 根据时间字符串长度匹配时间格式
     * @Param dateTime
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:38
     */
    private static String getPatternByLength(String dateTime) {
        if (dateTime == null) {
            throw new IllegalArgumentException();
        }
        int len = dateTime.length();
        String pattern = null;
        switch (len) {
            case 8:
                pattern = DateFormatEnum.YMD8.getDesc();
                break;
            case 10:
                pattern = DateFormatEnum.YMD10.getDesc();
                break;
            case 13:
                pattern = DateFormatEnum.YMDH.getDesc();
                break;
            case 14:
                pattern = DateFormatEnum.YMDHMS14.getDesc();
                break;
            case 16:
                pattern = DateFormatEnum.YMDHM.getDesc();
                break;
            case 19:
                pattern = DateFormatEnum.YMDHMS19.getDesc();
                break;
            case 25:
                pattern = DateFormatEnum.YMDTHMSZ.getDesc();
                break;
            default:
        }

        return pattern;
    }


    /**
     * 常规自动日期格式识别  返回对应的时间格式  yyyy-MM-dd   yyyy-MM-dd HH:mm:ss
     *
     * @param str 时间字符串
     * @return Date
     * @author dc
     */
    private static String getDateFormat(String str) {
        boolean year = false;
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        if (pattern.matcher(str.substring(0, 4)).matches()) {
            year = true;
        }
        StringBuilder sb = new StringBuilder();
        int index = 0;
        if (!year) {
            if (str.contains("月") || str.contains("-") || str.contains("/")) {
                if (Character.isDigit(str.charAt(0))) {
                    index = 1;
                }
            } else {
                index = 3;
            }
        }
        for (int i = 0; i < str.length(); i++) {
            char chr = str.charAt(i);
            if (Character.isDigit(chr)) {
                if (StringUtils.equalsAny(sb.toString(), "yyyy", "yyyyMM", "yyyyMMdd", "yyyyMMddHH", "yyyyMMddHHmm")) {
                    index++;
                }
                if (index == 0) {
                    sb.append("y");
                }
                if (index == 1) {
                    sb.append("M");
                }
                if (index == 2) {
                    sb.append("d");
                }
                if (index == 3) {
                    sb.append("H");
                }
                if (index == 4) {
                    sb.append("m");
                }
                if (index == 5) {
                    sb.append("s");
                }
                if (index == 6) {
                    sb.append("S");
                }
            } else {
                if (i > 0) {
                    char lastChar = str.charAt(i - 1);
                    if (Character.isDigit(lastChar)) {
                        index++;
                    }
                }
                sb.append(chr);
            }
        }
        return sb.toString();
    }


    /**
     * 根据日期字符串获取匹配的DateTimeFormatter，按字符串长度匹配，如果没有匹配的则抛错
     *
     * @Param dateTime
     * @Return java.time.format.DateTimeFormatter
     * @Author xubincheng
     * @Date 2023-01-30 17:55:10
     */
    public static DateTimeFormatter patternFormat(String dateTime) {
        String pattern = getPatternByLength(dateTime);
        if (pattern != null) {
            return getDefaultDateTimeFormatter(pattern);
        } else {
            throw new IllegalArgumentException("不支持的时间格式：" + dateTime);
        }
    }

    /**
     * @Description 获取默认的DateTimeFormatter，默认月1，日1，时0，没有默认会转换报错
     * @Param pattern
     * @Return java.time.format.DateTimeFormatter
     * @Author xubincheng
     * @Date 2022/1/30 20:43
     */
    public static DateTimeFormatter getDefaultDateTimeFormatter(String pattern) {
        return new DateTimeFormatterBuilder()
                .appendPattern(pattern)
                .parseDefaulting(ChronoField.MONTH_OF_YEAR, 1)
                .parseDefaulting(ChronoField.DAY_OF_MONTH, 1)
                .parseDefaulting(ChronoField.HOUR_OF_DAY, 0).toFormatter();
    }

    /**
     * @Description 获取追加N天的日期，例如：-1为昨日、0为今日、1为明日，格式为yyyy-MM-dd
     * @Param past
     * @Param format
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:43
     */
    public static String plusDays(long days, DateFormatEnum format) {
        return LocalDateTime.now().plusDays(days).format(DateTimeFormatter.ofPattern(format.getDesc()));
    }

    /**
     * 获取减去N天的日期
     *
     * @Param days
     * @Param format
     * @Return java.lang.String
     * @Author songwei
     * @Date 2023-03-21 16:39:20
     */
    public static String minusDays(long days, DateFormatEnum format) {
        return LocalDateTime.now().minusDays(days).format(DateTimeFormatter.ofPattern(format.getDesc()));
    }

    /**
     * @Description 获取包含今日的七日列表
     * @Param
     * @Return java.lang.String[]
     * @Author xubincheng
     * @Date 2022/1/30 20:45
     */
    public static String[] getWeekDays() {
        String[] days = new String[7];
        for (int i = 0; i < days.length; i++) {
            days[i] = plusDays(i - 6, DateFormatEnum.YMD10);
        }
        return days;
    }

    /**
     * @Description 获取当前时间距离下一个整点的秒数
     * @Param
     * @Return long
     * @Author xubincheng
     * @Date 2022/1/30 20:45
     */
    public static long remainSecUntilTheNextHour() {
        String nextHour = LocalDateTime.now().plusHours(1).format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDH.getDesc()));
        return diffSeconds(nextHour, today19());
    }

    /**
     * @Description 获取经过多长时间的文字描述
     * @Param targetTime
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2022/1/30 20:47
     */
    public static String getPassDateDesc(String targetTime) {
        if (StringUtils.isBlank(targetTime)) {
            return "刚刚";
        }
        long diffSeconds = DateUtils.diffSeconds(DateUtils.today19(), targetTime);
        if (diffSeconds < SECOND_MINUTE) {
            return "刚刚";
        } else if (diffSeconds < SECOND_HOUR) {
            return diffSeconds / SECOND_MINUTE + "分钟前";
        } else if (diffSeconds < SECOND_DAY) {
            return diffSeconds / SECOND_HOUR + "小时前";
        } else if (diffSeconds < SECOND_MONTH) {
            return diffSeconds / SECOND_DAY + "天前";
        } else if (diffSeconds < SECOND_YEAR) {
            return diffSeconds / SECOND_MONTH + "月前";
        }
        return "一年前";
    }

    /**
     * @Description 计算时间相差时间，搬过来的，不太明白
     * @Param startDate
     * @Param endDate
     * @Return java.lang.Long
     * @Author caijianhui
     * @Date 2022/1/30 20:55
     */
    public static Long getDatePoorMinutes(Date startDate, Date endDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;
        long diff = endDate.getTime() - startDate.getTime();
        //天数
        long day = diff / nd;
        //小时
        long hour = diff % nd / nh;
        if (day > 0) {
            hour = day * 24 + hour;
        }
        return hour;
    }

    /**
     * @Description 比较日期大小
     * @Param date1
     * @Param date2
     * @Return int
     * @Author xubincheng
     * @Date 2022/1/30 20:48
     */
    public static int compareTo(String date1, String date2) {
        LocalDateTime dt1 = LocalDateTime.parse(date1, patternFormat(date1));
        LocalDateTime dt2 = LocalDateTime.parse(date2, patternFormat(date2));
        return dt1.compareTo(dt2);
    }

    /**
     * 获取今天 N 个月前的日期
     *
     * @Param months
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-01-30 17:54:56
     */
    public static String todayMinusMonths(long months) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.minusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    /**
     * Description: 获取当前时间 N 天前的日期
     * Author: DB-SW
     * Date: 2023-11-08 15:39:34
     */
    public static String todayMinusDays(long days) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.minusDays(days);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    public static String todayMinusDays(long days, DateFormatEnum dateFormatEnum) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.minusDays(days);
        return date.format(DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }

    /**
     * Description: 获取当前时间 N 分钟后的时间
     * Author: DB-SW
     * Date: 2023-05-25 10:54:56
     */
    public static String todayPlusMinutes(long minutes) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.plusMinutes(minutes);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    /**
     * 获取今天 N 个月后的日期
     *
     * @Param months
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-01-30 17:54:49
     */
    public static String todayPlusMonths(long months) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime date = now.plusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    /**
     * Description: 获取指定日期N天前的时间
     * Author: DB-SW
     * Date: 2023-11-09 17:43:32
     */
    public static String dateMinusDays(String dateStr, long months, DateFormatEnum dateFormatEnum) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.minusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }

    /**
     * Description: 获取指定日期 N 个月前的日期，返回yyyy-MM-DD HH:mm:ss
     *
     * @Params:
     * @Return:
     * @author: songwei
     * @since: 2022-04-06 17:03:38
     **/
    public static String dateMinusMonths(String dateStr, long months) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.minusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    public static String dateMinusMonths(String dateStr, long months, DateFormatEnum dateFormatEnum) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, getDefaultDateTimeFormatter(getDateFormat(dateStr)));
        LocalDateTime date = dt.minusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }


    /**
     * 获取指定日期 N 个月后的日期
     *
     * @Param dateStr
     * @Param months
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-01-30 17:54:35
     */
    public static String datePlusMonths(String dateStr, long months) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.plusMonths(months);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    /**
     * 获取指定日期 N 个天后的日期
     *
     * @Param dateStr
     * @Param days
     * @Return java.lang.String
     * @Author caijianhui
     * @Date 2023-01-16 14:22:48
     */
    public static String datePlusDays(String dateStr, long days) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.plusDays(days);
        return date.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMD10.getDesc()));
    }

    /**
     * 获取指定日期N各小时后的日期
     *
     * @Param dateStr
     * @Param hours
     * @Param dateFormatEnum
     * @Return java.lang.String
     * @Author songwei
     * @Date 2023-04-07 13:44:58
     */
    public static String datePlusHours(String dateStr, long hours, DateFormatEnum dateFormatEnum) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.plusHours(hours);
        return date.format(DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }

    /**
     * 获取指定日期 N 秒后的日期
     *
     * @Param dateStr
     * @Param days
     * @Return java.lang.String
     * @Author caijianhui
     * @Date 2023-01-16 14:22:48
     */
    public static String datePlusSec(String dateStr, long sec, DateFormatEnum fmt) {
        LocalDateTime dt = LocalDateTime.parse(dateStr, patternFormat(dateStr));
        LocalDateTime date = dt.plusSeconds(sec);
        return date.format(DateTimeFormatter.ofPattern(fmt.getDesc()));
    }

    /**
     * 获取本月第一天
     *
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-01-30 17:54:26
     */
    public static String currentMonthMin(DateFormatEnum formatEnum) {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth())), LocalTime.MIN);
        return localDateTime.format(DateTimeFormatter.ofPattern(formatEnum.getDesc()));
    }

    /**
     * 获取本月最后一天
     *
     * @Param
     * @Return java.lang.String
     * @Author xubincheng
     * @Date 2023-01-30 17:54:20
     */
    public static String currentMonthMaxDate() {
        LocalDateTime localDateTime = LocalDateTime.of(LocalDate.from(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())), LocalTime.MAX);
        return localDateTime.format(DateTimeFormatter.ofPattern(DateFormatEnum.YMDHMS19.getDesc()));
    }

    /**
     * Description: 获取间隔月份的日期列表，返回格式YYYY-MM
     *
     * @Params:
     * @Return:
     * @author: songwei
     * @since: 2022-04-07 10:01:00
     **/
    public static List<String> intervalMonthDateList(String dateMin, String dateMax) {
        List<String> list = new ArrayList<>();
        LocalDateTime min = LocalDateTime.parse(dateMin, patternFormat(dateMin));
        LocalDateTime max = LocalDateTime.parse(dateMax, patternFormat(dateMax));

        DateTimeFormatter formatterYM = getDefaultDateTimeFormatter(DateFormatEnum.YM.getDesc());
        while (min.compareTo(max) <= 0) {
            String format = min.format(formatterYM);
            list.add(format);
            // 加一个月
            min = min.plusMonths(1);
        }
        return list;
    }

    /**
     * 匹配时间格式
     *
     * @Param time
     * @Param dateFormat
     * @Return boolean
     * @Author xubincheng
     * @Date 2023-01-30 16:53:50
     */
    public static boolean matchFormat(String time, DateFormatEnum dateFormat) {
        if (StringUtils.isBlank(time)) {
            return false;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat.getDesc());
        try {
            LocalDate.parse(time, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    public static LocalDate getIdBirthday(String idCard) {
        idCard = StringUtils.trim(idCard);
        if (StringUtils.isEmpty(idCard)) {
            return null;
        } else {
            String birthday = null;
            if (idCard.length() == 15) {
                birthday = "19" + idCard.trim().substring(6, 12);
            } else if (idCard.length() == 18) {
                birthday = idCard.trim().substring(6, 14);
            }

            if (birthday == null) {
                return null;
            } else {
                try {
                    return LocalDate.parse(birthday, DateTimeFormatter.ofPattern(DateFormatEnum.YMD8.getDesc()));
                } catch (Exception var3) {
                    log.error(var3.getMessage(), var3);
                    return null;
                }
            }
        }
    }

    /**
     * Description: 获取LocalDate
     * Author: DB-SW
     * Date: 2023-04-24 10:32:28
     */
    public static LocalDate getLocalDate(String date, DateFormatEnum dateFormatEnum) {
        if (StringUtils.isBlank(date)) {
            return null;
        }
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }

    public static LocalDate strRemoveFormat2LocalDate(String dateStr, DateFormatEnum dateFormatEnum) {
        String temp = dateStr.replace("-", "").replace("/", "");
        return LocalDate.parse(temp, DateTimeFormatter.ofPattern(dateFormatEnum.getDesc()));
    }

    /**
     * Description: 根据证件号获取年龄
     * Author: DB-SW
     * Date: 2023-04-30 18:42:57
     */
    public static String getAgeByCertNo(String certNo) {
        LocalDate birthday = DateUtils.getIdBirthday(certNo);
        if (birthday == null) {
            return "0";
        }
        return DateUtils.getAgeByBirth(birthday.toString());
    }

    /**
     * Description: 获取年龄
     * Author: DB-SW
     * Date: 2023-04-30 18:41:17
     */
    public static String getAgeByBirth(String birth) {
        if (StringUtils.isBlank(birth)) {
            return "0";
        }
        Date birthDate = DateUtils.parseDate(birth, DateFormatEnum.YMD10);
        Date now = new Date();
        if (birthDate == null || birthDate.after(now)) {
            return "0";
        }
        return DurationFormatUtils.formatPeriod(birthDate.getTime(), now.getTime(), "y");
    }

    /**
     * 获取星期（1到7 表示 周一到周日）
     *
     * @Param
     * @Return int
     * @Author xubincheng
     * @Date 2023-05-31 11:00:10
     */
    public static int getDayOfWeek() {
        return LocalDateTime.now().getDayOfWeek().getValue();
    }

    /**
     * 获取星期（1到7 表示 周一到周日）
     *
     * @Param
     * @Return int
     * @Author xubincheng
     * @Date 2023-05-31 11:00:10
     */
    public static int getDayOfWeek(Date date) {
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime().getDayOfWeek().getValue();
    }

    /**
     * 获取两个日期之间的日期
     *
     * @Param startDate
     * @Param endDate
     * @Param formatter
     * @Return java.util.List<java.time.LocalDate>
     * @Author caijianhui
     * @Date 2023-08-03 15:23:07
     */
    public static List<LocalDate> getDatesBetWeenUsing(String startDate, String endDate, String formatter) {
        LocalDate startParse = LocalDate.parse(startDate, DateTimeFormatter.ofPattern(formatter));
        LocalDate endParse = LocalDate.parse(endDate, DateTimeFormatter.ofPattern(formatter));
        long between = ChronoUnit.DAYS.between(startParse, endParse);
        List<LocalDate> collect = IntStream.iterate(0, i -> i + 1).limit(between).mapToObj(startParse::plusDays).collect(Collectors.toList());
        collect.add(endParse);
        return collect;
    }
}
