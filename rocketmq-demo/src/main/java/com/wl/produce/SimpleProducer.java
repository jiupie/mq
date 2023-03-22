package com.wl.produce;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * 简单生产者
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class SimpleProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();

        for (int i = 0; i < 5; i++) {
            Message message = new Message("simple-tp", ("hi," + i).getBytes(StandardCharsets.UTF_8));
            defaultMQProducer.send(message);
        }
        defaultMQProducer.shutdown();
    }
}
