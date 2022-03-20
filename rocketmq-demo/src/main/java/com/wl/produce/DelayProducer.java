package com.wl.produce;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * 延迟消息生产者
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class DelayProducer {
    public static void main(String[] args) throws  Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();

        for (int i = 0; i < 2; i++) {
            Message message = new Message("delay_tp",("msg"+i).getBytes(StandardCharsets.UTF_8));
            message.setDelayTimeLevel(2);
            defaultMQProducer.send(message);
        }


    }
}
