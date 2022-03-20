package com.wl.produce;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class AsyncProducer {
    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();
        for (int i = 0; i < 100; i++) {
            Message message = new Message("async-tp",("hello"+i).getBytes(StandardCharsets.UTF_8));
            defaultMQProducer.send(message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功:"+sendResult);
                }
                @Override
                public void onException(Throwable e) {
                    System.out.println("发送失败");
                }
            });
            //异步需要睡眠一下，不然还没发送完就 defaultMQProducer 生产者就关了
            Thread.sleep(1_000);
        }
        defaultMQProducer.shutdown();

    }
}
