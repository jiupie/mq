package com.wl.rabbi.consumer.service;

import com.rabbitmq.client.Channel;
import com.wl.mq.model.domain.Order;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Service
public class FanoutConsumerService {

    @RabbitListener(queues = "queue1")
    @RabbitHandler
    public void fanoutMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        Long tag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("fanout:"+order);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag,false,true);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "queue2")
    @RabbitHandler
    public void fanoutMessage2(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        Long tag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("fanout2:"+order);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag,false,true);
            e.printStackTrace();
        }
    }
}
