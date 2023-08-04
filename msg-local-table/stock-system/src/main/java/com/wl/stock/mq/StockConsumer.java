package com.wl.stock.mq;

import com.wl.mq.model.dto.OrderConfirmDTO;
import com.wl.mq.model.dto.OrderDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Random;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 11:16
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "stock-order-consumer-group", topic = "local_order_tp")
public class StockConsumer implements RocketMQListener<OrderDTO> {

    @Resource
    private MessageConfirmProducer messageConfirmProducer;

    @Override
    public void onMessage(OrderDTO message) {
        //1.是否已经消费过了

        log.info("接收到订单消息：{}", message);
        OrderConfirmDTO orderConfirmDTO = new OrderConfirmDTO();
        orderConfirmDTO.setOrderId(message.getId());

        //2.模拟消息消费失败
        if (new Random().nextInt(10) > 5) {
            messageConfirmProducer.sendAsyncCallback(orderConfirmDTO);
        }
    }
}
