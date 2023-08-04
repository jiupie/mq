package com.wl.stock.mq;

import com.wl.mq.constant.MQConstant;
import com.wl.mq.model.dto.OrderConfirmDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 11:17
 */
@Component
@Slf4j
public class MessageConfirmProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void sendAsyncCallback(OrderConfirmDTO orderConfirmDTO) {
        rocketMQTemplate.asyncSend(MQConstant.ORDER_CONFIRM_TOPIC, orderConfirmDTO, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable e) {

            }
        });
    }
}



