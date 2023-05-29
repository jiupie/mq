package com.wl.rk.producer;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
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

    public void sendAsyncCallback(String topic, String msg){
        rocketMQTemplate.asyncSend(topic, msg, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                System.out.println(sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                throwable.printStackTrace();
            }
        });
    }
}
