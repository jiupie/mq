package com.wl.rabbit.service;

import com.wl.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * 简单的先队列中发送消息
 * workQueue模式
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@RequiredArgsConstructor
@Service
public class WorkQueue {
    private final RabbitTemplate rabbitTemplate;

    public void send(Order order, String queueName) {
        //确认机制 只要成功发送到交换机就返回正确
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm被执行了.......");
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息发送失败没有到达指定的exchange");
                System.out.println("失败原因：" + cause);
            }
        });

        rabbitTemplate.setMandatory(true);
        //返回机制   - 如果消息没有路由到queue或者交换机，返回消息发送方（returncallback）
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("错误码：" + returned.getReplyCode());
            System.out.println("错误内容：" + returned.getReplyText());
            System.out.println("交换机：" + returned.getExchange());
            System.out.println("路由键（在work queue模式下是队列名）：" + returned.getRoutingKey());
            System.out.println(new String(returned.getMessage().getBody()));
        });
        //发送到work队列中
        rabbitTemplate.convertAndSend(queueName, order);
    }

    public void publish(Order order, String queueName) {
        //确认机制 只要成功发送到交换机就返回正确
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm被执行了.......");
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息发送失败没有到达指定的exchange");
                System.out.println("失败原因：" + cause);
            }
        });

        rabbitTemplate.setMandatory(true);
        //返回机制   - 如果消息没有路由到queue或者交换机，返回消息发送方（returncallback）
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("错误码：" + returned.getReplyCode());
            System.out.println("错误内容：" + returned.getReplyText());
            System.out.println("交换机：" + returned.getExchange());
            System.out.println("路由键（在work queue模式下是队列名）：" + returned.getRoutingKey());
            System.out.println(new String(returned.getMessage().getBody()));
        });

        //发送到work队列中
        rabbitTemplate.convertAndSend("fanout","", order);
    }

    public void direct(Order order, String routeKey) {
        //确认机制 只要成功发送到交换机就返回正确
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm被执行了.......");
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息发送失败没有到达指定的exchange");
                System.out.println("失败原因：" + cause);
            }
        });

        rabbitTemplate.setMandatory(true);
        //返回机制   - 如果消息没有路由到queue或者交换机，返回消息发送方（returncallback）
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("错误码：" + returned.getReplyCode());
            System.out.println("错误内容：" + returned.getReplyText());
            System.out.println("交换机：" + returned.getExchange());
            System.out.println("路由键（在work queue模式下是队列名）：" + returned.getRoutingKey());
            System.out.println(new String(returned.getMessage().getBody()));
        });

        //发送到work队列中
        rabbitTemplate.convertAndSend("direct",routeKey, order);
    }

    public void topic(Order order, String routeKey) {
        //确认机制 只要成功发送到交换机就返回正确
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm被执行了.......");
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息发送失败没有到达指定的exchange");
                System.out.println("失败原因：" + cause);
            }
        });

        rabbitTemplate.setMandatory(true);
        //返回机制   - 如果消息没有路由到queue或者交换机，返回消息发送方（returncallback）
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("错误码：" + returned.getReplyCode());
            System.out.println("错误内容：" + returned.getReplyText());
            System.out.println("交换机：" + returned.getExchange());
            System.out.println("路由键（在work queue模式下是队列名）：" + returned.getRoutingKey());
            System.out.println(new String(returned.getMessage().getBody()));
        });

        //发送到work队列中
        rabbitTemplate.convertAndSend("topic",routeKey, order);
    }

    public void dead(Order order, String routeKey) {
        //确认机制 只要成功发送到交换机就返回正确
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("confirm被执行了.......");
            if (ack) {
                System.out.println("消息成功发送到exchange");
            } else {
                System.out.println("消息发送失败没有到达指定的exchange");
                System.out.println("失败原因：" + cause);
            }
        });

        rabbitTemplate.setMandatory(true);
        //返回机制   - 如果消息没有路由到queue或者交换机，返回消息发送方（returncallback）
        rabbitTemplate.setReturnsCallback(returned -> {
            System.out.println("错误码：" + returned.getReplyCode());
            System.out.println("错误内容：" + returned.getReplyText());
            System.out.println("交换机：" + returned.getExchange());
            System.out.println("路由键（在work queue模式下是队列名）：" + returned.getRoutingKey());
            System.out.println(new String(returned.getMessage().getBody()));
        });

        //发送到work队列中
        rabbitTemplate.convertAndSend("topic1",routeKey, order);
    }
}
