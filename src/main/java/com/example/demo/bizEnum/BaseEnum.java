package com.example.demo.bizEnum;


import java.util.HashMap;
import java.util.Map;

/**
 * @Description 枚举类接口
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2022/1/30 20:53
 */
public interface BaseEnum {
    String getCode();

    String getDesc();

    default boolean equals(String code) {
        return getCode().equals(code);
    }

    static <T extends BaseEnum> T getByCode(String code, Class<T> enumClass) {
        if (!BaseEnum.class.isAssignableFrom(enumClass)) {
            throw new IllegalArgumentException();
        }
        for (T e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }

    static String getDesc(String code, Class<? extends BaseEnum> enumClass) {
        for (BaseEnum e : enumClass.getEnumConstants()) {
            if (e.getCode().equals(code)) {
                return e.getDesc();
            }
        }
        return "";
    }

    static Map<String, String> toMap(Class<? extends BaseEnum> enumClass) {
        Map<String, String> map = new HashMap<>();
        for (BaseEnum e : enumClass.getEnumConstants()) {
            map.put(e.getCode(), e.getDesc());
        }
        return map;
    }

}
