package com.example.demo.bizEnum;


/**
 * @Description 日期格式枚举
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2022/1/30 20:11
 */
public enum DateFormatEnum implements BaseEnum {
    Y("yyyy"),
    M("MM"),
    D("dd"),
    H("HH"),
    YM("yyyy-MM"),
    HM("HH:mm"),
    YMD8("yyyyMMdd"),
    YMD10("yyyy-MM-dd"),
    YMD10_START_TIME("yyyy-MM-dd 00:00:00"),
    YMD10_END_TIME("yyyy-MM-dd 23:59:59"),
    YMDH("yyyy-MM-dd HH"),
    YMDHM("yyyy-MM-dd HH:mm"),
    YMDHMS14("yyyyMMddHHmmss"),
    YMDHMS19("yyyy-MM-dd HH:mm:ss"),
    YMDTHMSZ("yyyy-MM-dd'T'HH:mm:ss+08:00"),

    YYYYMMDDHH("yyyyMMddHH");

    private final String desc;

    DateFormatEnum(String desc) {
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getDesc() {
        return this.desc;
    }

    public static DateFormatEnum getByCode(String code) {
        return BaseEnum.getByCode(code, DateFormatEnum.class);
    }

    public static String getDesc(String code) {
        return BaseEnum.getDesc(code, DateFormatEnum.class);
    }
}
