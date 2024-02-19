package com.example.demo.dto;

import com.example.demo.util.DateUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @Description 公共消息响应体
 * @Author xubincheng
 * @Company hangzhou-Dooban
 * @Date 2022/2/4 13:31
 */
@Getter
@Setter
public class ResultMsg {
    private static final String ACK = "ACK";
    public static final String NACK_CODE = "NACK";

    /**
     * 响应编码，成功为ACK，其余为异常编码
     */
    private String code;
    /**
     * 响应消息
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;
    /**
     * 错误类型
     */
    private String errType;
    /**
     * 错误提示（存在错误消息需要提示）
     */
    private String errTips;
    /**
     * 服务器时间，格式yyyy-MM-dd HH:mm:ss
     */
    private String serverTime;
    /**
     * 服务器时间戳（毫秒）
     */
    private Long timestamp;

    /**
     * 堆栈信息
     */
    private String stackTrace;

    private ResultMsg() {
    }

    public ResultMsg(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
        this.serverTime = DateUtils.today19();
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * @Description 获取成功响应体
     * @Param message 响应消息
     * @Return com.rzline.tradeplatform.framework.base.ResultMsg 响应体
     * @Author xubincheng
     * @Date 2022/2/4 13:37
     */
    public static ResultMsg toACK(String message) {
        return new ResultMsg(ACK, message, null);
    }

    public static ResultMsg toNACK(String message) {
        return new ResultMsg(NACK_CODE, message, null);
    }

    /**
     * @Description 获取成功响应体
     * @Param message 响应消息
     * @Param data 响应数据
     * @Return com.rzline.tradeplatform.framework.base.ResultMsg 响应体
     * @Author xubincheng
     * @Date 2022/2/4 13:40
     */
    public static ResultMsg toACK(String message, Object data) {
        return new ResultMsg(ACK, message, data);
    }

}
