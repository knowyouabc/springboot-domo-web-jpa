package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.demo.dto.BillOrderCollectData;
import com.example.demo.dto.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Slf4j
public class MyControllerTest {

    final static Map<String, String> TAI_LONG_REGEX; // 泰隆银行正则匹配规则集

    static {
        TAI_LONG_REGEX = new LinkedHashMap<>();
        TAI_LONG_REGEX.put("billNo", "电子银行承兑汇票.*?\\|票据\\(包\\)号：\\|(.*?)\\|");
        TAI_LONG_REGEX.put("subBillNo", "电子银行承兑汇票.*?\\|子票区间：\\|(.*?)\\|");
        TAI_LONG_REGEX.put("billAmount", "电子银行承兑汇票.*?\\|￥\\|([\\d\\|]+)");
        TAI_LONG_REGEX.put("dueDate", "电子银行承兑汇票.*?\\|汇票到期日：\\|(.*?)\\|");
        TAI_LONG_REGEX.put("acceptorName", "电子银行承兑汇票.*?\\|承兑人信息\\|全称\\|(.*?)\\|");
    }


    @RequestMapping(value = "/test")
    @ResponseBody
    public ResultMsg test(@RequestBody String data, HttpServletRequest request){
        BillOrderCollectData collectData = JSON.parseObject(data, BillOrderCollectData.class);
        if (collectData == null) {
            return ResultMsg.toACK("采集失败");
        }

        String content = collectData.getViewContent().replace("\n","").replace("\r","").replace("\u200B","");
//        String content = "欢迎登录浙江泰隆商业银行企业网上银行！||预留信息：|||1| || ||||票据查询||*|查询类型：||*|客户账号：||票据类型：||出票系统：||票据(包)号：||子票区间：||客户名称：|||票据(包)金额(￥)：|| 到 ||*|交易时间：|| 最近一个月 | 最近三个月 | 最近六个月 | 自定义 | 至 |查询|票据信息列表|票据(包)号|子票区间|出票人名称|出票人账号|收款人名称|收款人账号|承兑行|票据(包)金额|出票系统|起始日期|到期日|票据状态|距离到期天数|操作|--|阿尔法（江苏）重工科技有限公司|7066660011120128000424|安徽天康（集团）股份有限公司|34001737108059988888|苏州银行股份有限公司东台支行|8,409.00|ECDS|20230718|20230718|20231230|20231230|背书已签收|0天|总页数：|1|总记录数：|1|到第|页|导出|2015 浙江泰隆商业银行版权所有 | 浙ICP备11018676号 | 浙江省台州市路桥区南官大道188号 | 邮编：318050| |电子票据明细|close|close|打印|显示日期：|2024-01-31||电子银行承兑汇票|出票日期：|2023-07-18|汇票到期日：|2023-12-30|票据(包)号：|131331176001320230718603056831|子票区间：|--|票据状态：|背书已签收|流通状态：|--|出  票  人|出||票||人|全称|阿尔法（江苏）重工科技有限公司|收  票  人|收||票||人|全称|安徽天康（集团）股份有限公司|账号|7066660011120128000424||7066660011120128000424|账号|34001737108059988888||34001737108059988888|开户银行|苏州银行股份有限公司东台支行|开户银行|建行天长市支行|出票系统|ECDS|是否可拆分|否|出票保证信息|保证人名称： 保证人地址： 保证日期： |保证人名称：||保证人地址：||保证日期：||票据(包)金额|人民币 (大写) 捌仟肆佰零玖元整|人民币 (大写)|人民币|(大写)|捌仟肆佰零玖元整||十|亿|千|百|十|万|千|百|十|元|角|分|||||||￥|8|4|0|9|0|0|承兑人信息|全称|苏州银行股份有限公司东台支行|账号|0|开户行行号|开户行名称|313311760013|苏州银行股份有限公司东台支行|交易合同号||承 兑 信 息|承|兑|信|息|出票人承诺：本汇票请予以承兑，到期无条件付款|能否转让|可再转让|承兑人承兑：本汇票已经承兑，到期无条件付款 承兑日期：2023-07-18|承兑人承兑：本汇票已经承兑，到期无条件付款|承兑日期：|2023-07-18|承兑保证信息|保证人名称： 保证人地址： 保证日期： |保证人名称：||保证人地址：||保证日期：||评级信息（由出票人，承兑人自己记载，仅供参考）|出票人|评级主体： 信用等级： 评级到期日： |评级主体：||信用等级：||评级到期日：||承兑人|评级主体： 信用等级： 评级到期日： |评级主体：||信用等级：||评级到期日：|||免责声明：票据要素及票据权属关系以电子票据系统为准。|背书人名称|被背书人名称|背书日期|赎回开放日|赎回截止日|不得转让标记|备注|安徽天康（集团）股份有限公司|滁州市晶能光伏电力有限公司|20230719|||可转让||滁州市晶能光伏电力有限公司|晶科电力科技股份有限公司|20230719|||可转让||晶科电力科技股份有限公司|海宁市晶步光伏发电有限公司|20230721|||可转让||海宁市晶步光伏发电有限公司|陕西金源祥建设有限公司|20230721|||可转让||陕西金源祥建设有限公司|江苏久佳建设工程有限公司|20230728|||可转让||江苏久佳建设工程有限公司|上海啸剑机电设备有限公司|20230728|||可转让||上海啸剑机电设备有限公司|江苏揭谛国际贸易有限公司|20230824|||可转让||江苏揭谛国际贸易有限公司|南通格森仪器仪表制造有限公司|20230824|||可转让||南通格森仪器仪表制造有限公司|江苏揭谛国际贸易有限公司|20230824|||可转让||江苏揭谛国际贸易有限公司|南通中塘金属构件有限公司|20230824|||可转让||南通中塘金属构件有限公司|江苏揭谛国际贸易有限公司|20230824|||可转让||江苏揭谛国际贸易有限公司|南通卓康控制设备有限公司|20230825|||可转让||南通卓康控制设备有限公司|江苏揭谛国际贸易有限公司|20230825|||可转让||江苏揭谛国际贸易有限公司|南通双奥工业自动化设备有限公司|20230825|||可转让||南通双奥工业自动化设备有限公司|江苏揭谛国际贸易有限公司|20230825|||可转让||江苏揭谛国际贸易有限公司|上海啸剑机电设备有限公司|20230825|||可转让||上海啸剑机电设备有限公司|江苏揭谛国际贸易有限公司|20230901|||可转让||江苏揭谛国际贸易有限公司|江苏明峻景益能源有限公司|20230901|||可转让||江苏明峻景益能源有限公司|江苏揭谛国际贸易有限公司|20230901|||可转让||江苏揭谛国际贸易有限公司|上海明峻筝晨实业有限公司|20230901|||可转让||上海明峻筝晨实业有限公司|江苏揭谛国际贸易有限公司|20230901|||可转让||江苏揭谛国际贸易有限公司|上海啸剑机电设备有限公司|20231011|||可转让||上海啸剑机电设备有限公司|榆林市中银联商贸有限责任公司|20231019|||可转让||榆林市中银联商贸有限责任公司|上海啸剑机电设备有限公司|20231019|||可转让||上海啸剑机电设备有限公司|上海浜吾商务咨询有限公司|20231101|||可转让||上海浜吾商务咨询有限公司|上海啸剑机电设备有限公司|20231102|||可转让||上海啸剑机电设备有限公司|上海浜吾商务咨询有限公司|20231102|||可转让||上海浜吾商务咨询有限公司|上海啸剑机电设备有限公司|20231103|||可转让||上海啸剑机电设备有限公司|广东中侣铝业有限公司|20231113|||可转让||广东中侣铝业有限公司|上海啸剑机电设备有限公司|20231113|||可转让||上海啸剑机电设备有限公司|上海翔燊网络有限公司|20231114|||可转让|";

        JSONObject re = collectByRegex(content, TAI_LONG_REGEX);
        return ResultMsg.toACK("采集成功");
    }

    private JSONObject collectByRegex(String content, Map<String, String> regexMap) {
        JSONObject result = new JSONObject();
        TAI_LONG_REGEX.forEach((k, v) -> {
            try {
                String matched = matchByRegex(content, v);
                if (matched == null) {
                    log.warn("正则匹配结果为null，正则表达式为：{}，正文：{}", v, content);
                }

                result.put(k, matched);
            } catch (Exception e) {
                log.error("正则匹配异常，正则表达式为：{}，正文：{}", v, content);
            }
        });
        return result;
    }

    public static String matchByRegex(String content, String regexStr) {
        String matched = null;

        Pattern pattern = Pattern.compile(regexStr);
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            matched = matcher.group(1);
        }

        if (matched != null && matched.contains("|")) {
            matched = matched.replace("|", "");
        }

        return matched;
    }
}
