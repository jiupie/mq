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
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Service
public class TopicConsumerService {
    //item.# 以item开头的
    @RabbitListener(queues = "itemqueue1")
    @RabbitHandler
    public void topicMessage1(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        Long tag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("topic itemqueue1 item.#:"+order);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag,false,true);
            e.printStackTrace();
        }
    }

    //item.* 以item开头的，只匹配一个
    @RabbitListener(queues = "itemqueue2")
    @RabbitHandler
    public void topicMessage2(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        Long tag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("topic itemqueue2 item.*:"+order);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag,false,true);
            e.printStackTrace();
        }
    }

    //#.item 以item结尾的
    @RabbitListener(queues = "itemqueue3")
    @RabbitHandler
    public void topicMessage3(@Payload Order order, @Headers Map<String,Object> headers, Channel channel) throws IOException {
        Long tag= (Long) headers.get(AmqpHeaders.DELIVERY_TAG);
        try {
            System.out.println("topic itemqueue3 #.item :"+order);
            channel.basicAck(tag,false);
        } catch (Exception e) {
            System.out.println("消息重回队列");
            channel.basicNack(tag,false,true);
            e.printStackTrace();
        }
    }
}
