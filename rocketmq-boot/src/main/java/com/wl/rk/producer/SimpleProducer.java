package com.wl.rk.producer;

import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/21
 */
@Component
public class SimpleProducer {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendSimpleMsg(String topic, String msg) {
        rocketMQTemplate.convertAndSend(topic, msg);
    }
}
