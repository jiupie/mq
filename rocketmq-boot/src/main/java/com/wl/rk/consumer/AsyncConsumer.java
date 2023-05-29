package com.wl.rk.consumer;

import com.wl.rk.exception.MyException;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/4/16
 */
@Component
@RocketMQMessageListener(consumerGroup = "async-gp", topic = "async-boot")
public class AsyncConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String s) {
        if (s.equals("error")) {
            throw new MyException("回退消息");
        }
        System.out.println(s);
    }
}
