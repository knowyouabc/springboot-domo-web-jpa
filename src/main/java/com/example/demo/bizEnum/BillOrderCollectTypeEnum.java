package com.example.demo.bizEnum;

/**
 * 采集方式
 *
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2023-12-04 11:04:24
 */
public enum BillOrderCollectTypeEnum implements BaseEnum {
    SINGLE("单张采集"),
    BATCH("批量采集"),
    ;

    private final String desc;

    BillOrderCollectTypeEnum(String desc) {
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

    public static BillOrderCollectTypeEnum getByCode(String code) {
        return BaseEnum.getByCode(code, BillOrderCollectTypeEnum.class);
    }

    public static String getDesc(String code) {
        return BaseEnum.getDesc(code, BillOrderCollectTypeEnum.class);
    }
}
