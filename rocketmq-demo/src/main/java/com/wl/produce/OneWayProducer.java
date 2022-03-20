package com.wl.produce;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class OneWayProducer {
    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();

        for (int i = 0; i < 3; i++) {
            Message message = new Message("async-tp",("hello"+i).getBytes(StandardCharsets.UTF_8));
            defaultMQProducer.sendOneway(message);
            Thread.sleep(1_000);
        }
        defaultMQProducer.shutdown();

    }
}
