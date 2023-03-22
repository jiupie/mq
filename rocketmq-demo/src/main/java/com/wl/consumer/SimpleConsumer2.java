package com.wl.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import java.util.List;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class SimpleConsumer2 {
    public static void main(String[] args) throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("cm-gp3");
        consumer.setNamesrvAddr("127.0.0.1:9876");
        consumer.subscribe("simple-tp","*");
        consumer.setMessageModel(MessageModel.CLUSTERING);

        //-1表示16次，如果消息在成功之前被重新消费超过maxReconsumeTimes，则将其重定向到等待删除的队列中。
        consumer.setMaxReconsumeTimes(-1);

        //消息监听器
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.println(msgs);
                throw  new RuntimeException("error");
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
    }
}
