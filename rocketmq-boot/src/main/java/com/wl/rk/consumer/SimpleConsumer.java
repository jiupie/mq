package com.wl.rk.consumer;

import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/21
 */
@Component
@RocketMQMessageListener(consumerGroup = "simple-cg-boot", topic = "simple-tp-boot")
public class SimpleConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String o) {
        System.out.println(o);
        throw new RuntimeException("消费错误");
    }
}
