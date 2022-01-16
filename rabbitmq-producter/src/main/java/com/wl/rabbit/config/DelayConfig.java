package com.wl.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 死信队列
 * 延时
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Configuration
public class DelayConfig {

    //    正常队列和交换机

    @Bean
    public Queue customQueue1() {
        return QueueBuilder
                .durable("customQueue1")
                .withArgument("x-dead-letter-exchange","deadExchange")
                .withArgument("x-dead-letter-routing-key","cancel")
                .maxLength(5)
                .build();
    }

    @Bean
    public Queue customQueue2() {
        return QueueBuilder.durable("customQueue2")
                .withArgument("x-dead-letter-exchange","deadExchange")
                .withArgument("x-dead-letter-routing-key","delay")
                .ttl(1_000)
                .build();
    }

    @Bean
    public Exchange topicExchange1() {
        return ExchangeBuilder.topicExchange("topic1")
                .durable(true)
                .build();
    }

    @Bean
    public Binding binding12(@Qualifier("topicExchange1") Exchange topicExchange1,
                             @Qualifier("customQueue1") Queue customQueue1){
        return BindingBuilder.bind(customQueue1).to(topicExchange1).with("order").noargs();
    }

    @Bean
    public Binding binding23(@Qualifier("topicExchange1") Exchange topicExchange1,
                             @Qualifier("customQueue2") Queue customQueue2){
        return BindingBuilder.bind(customQueue2).to(topicExchange1).with("msg").noargs();
    }

    //死信交换机和死信队列

    @Bean
    public Exchange deadExchange(){
        return ExchangeBuilder.directExchange("deadExchange").durable(true).build();
    }


    @Bean
    public Queue deadQueue1(){
        return QueueBuilder.durable("deadQueue1").build();
    }

    @Bean
    public Queue deadQueue2(){
        return QueueBuilder.durable("deadQueue2").build();
    }

    @Bean
    public Binding deadBinding1(@Qualifier("deadExchange") Exchange deadExchange,
                                @Qualifier("deadQueue1") Queue deadQueue1){
        return BindingBuilder.bind(deadQueue1).to(deadExchange).with("cancel").noargs();
    }

    @Bean
    public Binding deadBinding2(@Qualifier("deadExchange") Exchange deadExchange,
                                @Qualifier("deadQueue2") Queue deadQueue2){
        return BindingBuilder.bind(deadQueue2).to(deadExchange).with("delay").noargs();
    }
}
