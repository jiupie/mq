package com.wl.order.service.impl;

import com.alibaba.fastjson.JSON;
import com.wl.mq.constant.MQConstant;
import com.wl.mq.model.dto.OrderDTO;
import com.wl.order.enums.MessageRecordStatusEnum;
import com.wl.order.mapper.OmsOrderMapper;
import com.wl.order.model.entity.MessageRecord;
import com.wl.order.model.entity.OmsOrder;
import com.wl.order.mq.OrderProducer;
import com.wl.order.service.MessageRecordService;
import com.wl.order.service.OmsOrderService;
import com.wl.order.support.OrderConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;


@Service
public class OmsOrderServiceImpl implements OmsOrderService {
    @Resource
    private OmsOrderMapper omsOrderMapper;

    @Resource
    private OrderProducer orderProducer;

    @Resource
    private MessageRecordService messageRecordService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void createOrder(OmsOrder omsOrder) {
        omsOrderMapper.insert(omsOrder);

        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setBusinessId(String.valueOf(omsOrder.getId()));
        messageRecord.setBusinessType(OrderConstant.MESSAGE_TYPE);
        messageRecord.setMsgText(JSON.toJSONString(omsOrder));
        messageRecord.setMsgStatus(MessageRecordStatusEnum.NOT_SEND.getCode());
        messageRecord.setCreateTime(new Date());
        messageRecord.setTopic(MQConstant.ORDER_PRODUCER_TOPIC);
        messageRecordService.save(messageRecord);

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(omsOrder.getId());
        orderDTO.setMoney(omsOrder.getTotalAmount());

        orderProducer.sendAsyncCallback(orderDTO);
    }
}




