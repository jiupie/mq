package com.wl.produce;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/19
 */
public class BatchProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("batch_group");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");

        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();

        ArrayList<Message> msgs = new ArrayList<>();
        Message message = new Message("batch-tp","msg1".getBytes(StandardCharsets.UTF_8));
        msgs.add(message);
        Message message2 = new Message("batch-tp","msg2".getBytes(StandardCharsets.UTF_8));

        msgs.add(message2);
        defaultMQProducer.send(msgs);
    }
}
