package com.wl.produce;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 顺序消息生产者
 *
 * @author 南顾北衫
 * @email 17674641491@163.com
 */
public class OrderProducer {
    public static List<Order> getOrders() {
        ArrayList<Order> list = new ArrayList<>();

        Order order = new Order(1239, "订单未支付");
        Order order111 = new Order(129, "订单未支付");

        Order order1 = new Order(1239, "订单已支付");
        Order order11 = new Order(129, "订单已支付");

        Order order2 = new Order(1239, "订单已发货中");
        Order order22 = new Order(129, "订单已发货中");

        Order order3 = new Order(1239, "订单已发");
        Order order33 = new Order(129, "订单已发");

        list.add(order);
        list.add(order111);

        list.add(order1);
        list.add(order11);

        list.add(order2);
        list.add(order22);

        list.add(order3);
        list.add(order33);
        return list;
    }

    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer("pg");
        defaultMQProducer.setNamesrvAddr("127.0.0.1:9876");
        defaultMQProducer.setRetryTimesWhenSendAsyncFailed(3);
        defaultMQProducer.setSendMsgTimeout(6_000);
        defaultMQProducer.start();

        List<Order> orders = getOrders();
        for (int i = 0; i < orders.size(); i++) {
            Order order = orders.get(i);
            Message message = new Message("order-tp", "tgf","key" + i, order.toString().getBytes(StandardCharsets.UTF_8));
            defaultMQProducer.send(message, (List<MessageQueue> mqs, Message msg, Object arg) -> {
                Integer orderId = (Integer) arg;
                int size = mqs.size();
                int index = orderId % size;
                return mqs.get(index);
            }, order.getOrderId());
            //异步需要睡眠一下，不然还没发送完就 defaultMQProducer 生产者就关了
            Thread.sleep(1_000);
        }


        defaultMQProducer.shutdown();


    }
}
