package com.example.demo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.example.demo.bizEnum.BillOrderBillSourceEnum;
import com.example.demo.bizEnum.BillOrderCollectTypeEnum;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

/**
 * BillOrderCollectData 票据采集数据
 *
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2023-11-30 10:45:31
 */
@Getter
@Setter
public class BillOrderCollectData {
    public interface CollectGroup {
    }

    /**
     * 客户ID
     */
    @NotNull(message = "customerId不能为空", groups = CollectGroup.class)
    private Long customerId;
    /**
     * 视图识别内容
     */
    @NotNull(message = "viewContent不能为空", groups = CollectGroup.class)
    private String viewContent;
    /**
     * 是否为浏览器
     */
    @JSONField(name = "isBrowser")
    private Boolean browser;
    /**
     * 内容类型：TXT / HTML
     */
    private String contentType;
    /**
     * 网址
     */
    private String url;
    /**
     * 窗口名称
     */
    @NotNull(message = "handleName不能为空", groups = CollectGroup.class)
    private String handleName;
    /**
     * 采集方式
     */
    @NotNull(message = "采集方式不能为空", groups = CollectGroup.class)
    private BillOrderCollectTypeEnum type;
    /**
     * 票面图片正面
     */
    private String billPicA;

    /**
     * 票据来源
     */
    private BillOrderBillSourceEnum billSource;
}
