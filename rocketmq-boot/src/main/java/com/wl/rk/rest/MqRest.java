package com.wl.rk.rest;

import com.wl.rk.producer.SimpleProducer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/21
 */
@RequestMapping("/mq")
@RestController
public class MqRest {
    @Resource
    private SimpleProducer simpleProducer;

    @GetMapping
    public String f(String msg) {
        simpleProducer.sendSimpleMsg("simple-tp-boot",msg);
        return "success";
    }
}
