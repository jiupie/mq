package com.wl.order.mq;

import com.wl.mq.constant.MQConstant;
import com.wl.mq.model.dto.OrderConfirmDTO;
import com.wl.order.enums.MessageRecordStatusEnum;
import com.wl.order.model.dto.MessageRecordStatusDTO;
import com.wl.order.service.MessageRecordService;
import com.wl.order.support.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 11:04
 */
@Component
@Slf4j
@RocketMQMessageListener(topic = MQConstant.ORDER_CONFIRM_TOPIC, consumerGroup = "order-confirm-consumer-group")
public class OrderConfirmConsumer implements RocketMQListener<OrderConfirmDTO> {
    @Resource
    private MessageRecordService messageRecordService;

    @Override
    public void onMessage(OrderConfirmDTO message) {
        log.info("接收到订单确认消息：{}", message);
        //更改消息状态
        MessageRecordStatusDTO messageRecordStatusDTO = new MessageRecordStatusDTO();
        messageRecordStatusDTO.setBusinessId(message.getOrderId());
        messageRecordStatusDTO.setBusinessType(OrderConstant.MESSAGE_TYPE);
        messageRecordStatusDTO.setOldStatus(MessageRecordStatusEnum.SEND_SUCCESS.getCode());
        messageRecordStatusDTO.setNewStatus(MessageRecordStatusEnum.CONSUMER_SUCCESS.getCode());
        messageRecordService.changeStatusByBusiness(messageRecordStatusDTO);
    }
}
