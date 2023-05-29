package com.wl.produce;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 顺序消息生产者
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class OrderProducer {
    //sl4j logger
    private static final Logger logger = LoggerFactory.getLogger(OrderProducer.class);

    public static void main(String[] args) {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("order-pg");

        try {
            defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
            defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
            defaultMQProducer.setSendMsgTimeout(6_000);
            defaultMQProducer.start();

            //模拟10笔订单
            for (int orderId = 0; orderId < 10; orderId++) {
                //每笔订单发送3条消息，（1）创建订单（2）订单库存扣减（3）加积分
                for (int i = 0; i < 3; i++) {
                    String data = "";
                    switch (i) {
                        case 0:
                            data = orderId + "号订单创建完成";
                            break;
                        case 1:
                            data = orderId + "号订单库存扣减完成";
                            break;
                        case 2:
                            data = orderId + "号订单加积分完成";
                            break;
                    }
                    //创建消息对象
                    Message message = new Message("order-topic", "order-tag", orderId + "", data.getBytes());
                    //发送消息
                    defaultMQProducer.send(message, new MessageQueueSelector() {
                        //select方法的作用是选择发送到broker哪个队列
                        @Override
                        public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                            //获取订单id
                            Integer id = (Integer) arg;
                            //根据订单id选择队列
                            long index = id % mqs.size();
                            MessageQueue messageQueue = mqs.get((int) index);
                            logger.info("id:{},data:{},queue:{}", id, new String(msg.getBody()), messageQueue);
                            return messageQueue;
                        }
                    }, orderId);
                }
            }
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            //关闭生产者
            defaultMQProducer.shutdown();
        }


    }
}
