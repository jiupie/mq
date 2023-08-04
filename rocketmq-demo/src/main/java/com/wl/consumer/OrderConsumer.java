package com.wl.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 顺序消息消费者
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class OrderConsumer {
    //sl4j logger
    private static final Logger logger = LoggerFactory.getLogger(OrderConsumer.class);

    public static void main(String[] args) {
        try {
            DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order-cg");
            consumer.setNamesrvAddr("127.0.0.1:9876");

            consumer.subscribe("order-topic", "*");

            //注册消息监听器，需要实现MessageListenerOrderly接口，用于实现有序消费
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
                    for (MessageExt msg : msgs) {
                        String keys = msg.getKeys();
                        String s = new String(msg.getBody());
                        if ("1".equals(keys) && "0号订单库存扣减完成".equals(s)) {
                            int i=1/0;
                        }

                        logger.info("{},{},{}", keys, s, context.getMessageQueue());
                    }
                    //确认消费成功
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (MQClientException e) {
            throw new RuntimeException(e);
        } finally {
            logger.info("consumer start");
        }
    }
}
