package com.example.demo.controller;

import com.alibaba.fastjson.JSON;
import com.example.demo.dto.BillOrderCollectData;
import com.example.demo.dto.ResultMsg;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

@Api(tags = "用户接口")
@RestController
@RequestMapping("/bill-order")
//@Slf4j(topic = "customAppender")
@Slf4j
public class MyController {

    @GetMapping("/getdata")
    @ApiOperation(value = "getdata")
    public String getdata() {
        log.debug("aaaaaaa");
        log.info("aaaaaaa");
        log.error("aaaaaaa");
        return "getdata hello jenkins2";
    }

    @RequestMapping(value = "/postdata")
    @ApiOperation(value = "postdata")
    public String postdata() {
        log.info("bbbbbbbb");
        return "postdata hello";
    }

    int i =0;

    @PostMapping("/collect")
    public ResultMsg collect(@RequestBody BillOrderCollectData collectData, HttpServletRequest request) {
        i++;

        System.out.println(JSON.toJSONString(collectData));

        if (i%2==0) {
            return ResultMsg.toACK("采集成功");
        }
        else {
            return ResultMsg.toNACK("采集成功");
        }
    }
}
