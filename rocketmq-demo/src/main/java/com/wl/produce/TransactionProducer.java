package com.wl.produce;

import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;

/**
 * @author 南顾北衫
 * @description 分布式事务
 * @date 2022/3/20
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        TransactionMQProducer defaultMQProducer = new TransactionMQProducer("tran-pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);

        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();
        TransactionListener transactionListener = new MyTransactionListener();
        defaultMQProducer.setTransactionListener(transactionListener);
        defaultMQProducer.send(new Message("tranction-tp", "tagA", "hi,transaction".getBytes(StandardCharsets.UTF_8)));
        defaultMQProducer.send(new Message("tranction-tp", "tagB", "hi,transaction".getBytes(StandardCharsets.UTF_8)));
        defaultMQProducer.send(new Message("tranction-tp", "tagC", "hi,transaction".getBytes(StandardCharsets.UTF_8)));

//        defaultMQProducer.shutdown();
    }
}
