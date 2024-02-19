package com.example.demo.bizEnum;

/**
 * 票据订单票据来源枚举
 *
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2023-01-13 11:06:52
 */
public enum BillOrderBillSourceEnum implements BaseEnum {
    USER_PUSH("用户发布"),
    PQQ_PUSH("票签签推送"),
    KF_PUSH("客服代客发布"),
    CR_COLLECT("承让"),
    CD_COLLECT("承道"),
    ;

    private final String desc;

    BillOrderBillSourceEnum(String desc) {
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

    public static BillOrderBillSourceEnum getByCode(String code) {
        return BaseEnum.getByCode(code, BillOrderBillSourceEnum.class);
    }

    public static String getDesc(String code) {
        return BaseEnum.getDesc(code, BillOrderBillSourceEnum.class);
    }
}
