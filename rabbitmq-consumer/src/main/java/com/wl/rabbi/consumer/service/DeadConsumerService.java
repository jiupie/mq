package com.wl.rabbi.consumer.service;

import com.rabbitmq.client.Channel;
import com.wl.domain.Order;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * 正常交换机topic1 =》队列customQueue1 （order） （x-dead-letter-routing-key："cancel"，maxlength:5）
 *              =》队列customQueue2 (msg)（x-dead-letter-routing-key："delay"，ttl:1000ms）
 *
 * 死信交换机 deadExchange=》死信队列deadQueue1 （cancel）
 *                      =》死信队列deadQueue2 （delay）
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Service
public class DeadConsumerService {

    @RabbitListener(queues = "customQueue1")
    @RabbitHandler
    public void customMessage1(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("customQueue1 queue order:" + order);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag, false, true);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "customQueue2")
    @RabbitHandler
    public void customMessage2(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("customQueue2 queue msg:" + order);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag, false, true);
            e.printStackTrace();
        }
    }





    //死信队列


    @RabbitListener(queues = "deadQueue1")
    @RabbitHandler
    public void deadMessage(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("dead queue cancel:" + order);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag, false, true);
            e.printStackTrace();
        }
    }

    @RabbitListener(queues = "deadQueue2")
    @RabbitHandler
    public void deadMessage2(@Payload Order order, @Headers Map<String, Object> headers, Channel channel) throws IOException {
        Long tag = (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("dead queue delay:" + order);
            channel.basicAck(tag, false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag, false, true);
            e.printStackTrace();
        }
    }
}
