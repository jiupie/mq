package com.wl.order.mq;

import com.wl.mq.constant.MQConstant;
import com.wl.mq.model.dto.OrderDTO;
import com.wl.order.enums.MessageRecordStatusEnum;
import com.wl.order.model.dto.MessageRecordStatusDTO;
import com.wl.order.service.MessageRecordService;
import com.wl.order.support.OrderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 10:13
 */
@Component
@Slf4j
public class OrderProducer {
    @Resource
    private RocketMQTemplate rocketMQTemplate;
    @Resource
    private MessageRecordService messageRecordService;


    public void sendAsyncCallback(OrderDTO orderDTO) {
        rocketMQTemplate.asyncSend(MQConstant.ORDER_PRODUCER_TOPIC, orderDTO, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                Long id = orderDTO.getId();
                MessageRecordStatusDTO messageRecordStatusDTO = new MessageRecordStatusDTO();
                messageRecordStatusDTO.setBusinessId(id);
                messageRecordStatusDTO.setBusinessType(OrderConstant.MESSAGE_TYPE);
                messageRecordStatusDTO.setOldStatus(MessageRecordStatusEnum.NOT_SEND.getCode());
                messageRecordStatusDTO.setNewStatus(MessageRecordStatusEnum.SEND_SUCCESS.getCode());
                messageRecordService.changeStatusByBusiness(messageRecordStatusDTO);
                log.info("业务类型:{},业务id:{},发送消息成功", OrderConstant.MESSAGE_TYPE, orderDTO.getId());
            }

            @Override
            public void onException(Throwable throwable) {
                log.error("业务类型:{},业务id:{},发送消息失败:{}", OrderConstant.MESSAGE_TYPE, orderDTO.getId(), throwable.getMessage());
            }
        });
    }
}
