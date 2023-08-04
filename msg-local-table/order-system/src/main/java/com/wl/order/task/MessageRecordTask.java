package com.wl.order.task;

import com.alibaba.fastjson.JSON;
import com.wl.mq.model.dto.OrderDTO;
import com.wl.order.enums.MessageRecordStatusEnum;
import com.wl.order.model.entity.MessageRecord;
import com.wl.order.service.MessageRecordService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 11:13
 */
@Component
@Slf4j
public class MessageRecordTask {
    @Resource
    private MessageRecordService messageRecordService;

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    private final int MAX_RETIRES = 3;

    @Scheduled(cron = "0 5 * * * ?")
    public void messageTask() {
        List<MessageRecord> allNoConsumer = messageRecordService.getAllNoConsumer(MAX_RETIRES, DateUtils.addMinutes(new Date(), -4));
        log.info("执行定时任务,需要重发消息数量：{}", allNoConsumer.size());

        for (MessageRecord messageRecord : allNoConsumer) {
            rocketMQTemplate.asyncSend(messageRecord.getTopic(), JSON.parseObject(messageRecord.getMsgText(), OrderDTO.class), new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    messageRecordService.changeStatusByMessageId(messageRecord.getId(), MessageRecordStatusEnum.NOT_SEND.getCode(), MessageRecordStatusEnum.SEND_SUCCESS.getCode());
                }

                @Override
                public void onException(Throwable e) {
                    if (messageRecord.getRetriesNumber() + 1 >= MAX_RETIRES) {
                        log.info("消息发送失败，达到最大重试次数，消息id：{}", messageRecord.getId());
                    }
                    messageRecordService.incReties(messageRecord.getId());
                }
            });
        }
    }
}

