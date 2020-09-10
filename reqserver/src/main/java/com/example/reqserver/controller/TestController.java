package com.example.reqserver.controller;

import com.example.reqserver.bean.dto.TestDto;
import com.example.reqserver.bean.vo.TestVo;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@RequestMapping("/test")
@RestController
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    private final Gson gson = new Gson();

    @RequestMapping("/test")
    public void test() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        //请求测试数据
        TestDto testDto = new TestDto();
        testDto.setApplyUser("蕾米莉亚");
        testDto.setApplyPlace("红魔馆");
        testDto.setApplyDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        testDto.setApplyPay(25000000);
        String token = RequestHeadersBody.token();
        HttpHeaders header = RequestHeadersBody.getGatewayHeader(token);
        String body = RequestHeadersBody.getBodyContent(
                gson.fromJson(gson.toJson(testDto), HashMap.class), TestDto.class, token);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost:8081/test",
                new HttpEntity<>(body, header), String.class);
        //打印结果
        log.info(responseEntity.getBody());
        //log.info(ResponseHeaderBody.getBody(responseEntity, TestVo.class));
    }

}
