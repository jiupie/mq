package com.wl.produce;

import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 南顾北衫
 * @description 分布式事务
 * @date 2022/3/20
 */
public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        //事务消息通过TransactionMQProducer来发送
        TransactionMQProducer defaultMQProducer = new TransactionMQProducer("tran-pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);

        //设置线程池 用于事务回查
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        defaultMQProducer.setExecutorService(cachedThreadPool);
        defaultMQProducer.start();

        //绑定事务监听器，用于执行代码
        TransactionListener transactionListener = new MyTransactionListener();
        defaultMQProducer.setTransactionListener(transactionListener);

        //创建消息对象
        Message message = new Message("tranction-tp", "order","1030" ,"1030订单与明细完整的json".getBytes(StandardCharsets.UTF_8));

        //一定调用sendMessageInTransaction方法发送事务消息
        //参数1：消息对象
        //参数2：其他参数 一般为null
        defaultMQProducer.sendMessageInTransaction(message, null);

        defaultMQProducer.shutdown();
    }
}
