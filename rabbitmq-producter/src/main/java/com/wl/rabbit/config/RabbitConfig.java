package com.wl.rabbit.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
@Configuration
public class RabbitConfig {

    /**
     * workQueue队列工作模式
     *
     * @return /
     */
    @Bean
    public Queue work() {
        return new Queue("work", true, false, false);
    }


    //publish/subscribe模式

    @Bean
    public Exchange fanoutExchange() {
        return ExchangeBuilder.fanoutExchange("fanout").build();
    }

    @Bean
    public Queue queue1() {
        return QueueBuilder.durable("queue1").build();
    }

    @Bean
    public Queue queue2() {
        return QueueBuilder.durable("queue2").build();
    }

    @Bean
    public Binding binding(@Qualifier("queue1") Queue queue1, @Qualifier("queue2") Queue queue2, @Qualifier("fanoutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue1).to(exchange).with("").noargs();
    }

    @Bean
    public Binding binding1(@Qualifier("queue1") Queue queue1, @Qualifier("queue2") Queue queue2, @Qualifier("fanoutExchange") Exchange exchange) {
        return BindingBuilder.bind(queue2).to(exchange).with("").noargs();
    }

    //routing模式 direct

    @Bean
    public Exchange routingExchange() {
        return ExchangeBuilder.directExchange("direct").durable(true).build();
    }

    @Bean
    public Queue infoQueue() {
        return QueueBuilder.durable("info").build();
    }

    @Bean
    public Queue errorQueue() {
        return QueueBuilder.durable("error").build();
    }

    @Bean
    public Binding directBinding(@Qualifier("routingExchange") Exchange directExchange, @Qualifier("infoQueue") Queue infoQueue) {
        return BindingBuilder.bind(infoQueue).to(directExchange).with("info").noargs();
    }

    @Bean
    public Binding directBinding2(@Qualifier("routingExchange") Exchange directExchange, @Qualifier("errorQueue") Queue errorQueue) {
        return BindingBuilder.bind(errorQueue).to(directExchange).with("error").noargs();
    }
//////////////////

    //topic 模式

    @Bean
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange("topic").build();
    }

    @Bean
    public Queue itemQueue1(){
        return QueueBuilder.durable("itemqueue1").build();
    }

    @Bean
    public Queue itemQueue2(){
        return QueueBuilder.durable("itemqueue2").build();
    }

    @Bean
    public Queue itemQueue3(){
        return QueueBuilder.durable("itemqueue3").build();
    }

    @Bean
    public Binding topicBinding1(@Qualifier("topicExchange") Exchange topicExchange,
                                 @Qualifier("itemQueue1") Queue itemQueue1){
        //item.# 以item开头的
        return BindingBuilder.bind(itemQueue1).to(topicExchange).with("item.#").noargs();
    }

    @Bean
    public Binding topicBinding2(@Qualifier("topicExchange") Exchange topicExchange,
                                 @Qualifier("itemQueue2") Queue itemQueue2){
        //item.* 以item开头的，只匹配一个
        return BindingBuilder.bind(itemQueue2).to(topicExchange).with("item.*").noargs();
    }

    @Bean
    public Binding topicBinding3(@Qualifier("topicExchange") Exchange topicExchange,
                                 @Qualifier("itemQueue3") Queue itemQueue3){
        //#.item 以item结尾的
        return BindingBuilder.bind(itemQueue3).to(topicExchange).with("#.item").noargs();
    }

}
