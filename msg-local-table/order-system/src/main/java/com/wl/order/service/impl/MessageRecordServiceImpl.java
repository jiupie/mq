package com.wl.order.service.impl;

import com.wl.order.mapper.MessageRecordMapper;
import com.wl.order.model.dto.MessageRecordStatusDTO;
import com.wl.order.model.entity.MessageRecord;
import com.wl.order.service.MessageRecordService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 10:54
 */
@Service
public class MessageRecordServiceImpl implements MessageRecordService {
    @Resource
    private MessageRecordMapper messageRecordMapper;

    @Override
    public void save(MessageRecord messageRecord) {
        messageRecordMapper.insert(messageRecord);
    }

    @Override
    public void changeStatusByMessageId(long messageId, int oldStatus, int newStatus) {
        messageRecordMapper.changeStatusByMessageId(messageId, oldStatus, newStatus);
    }

    @Override
    public void incReties(long messageId) {
        messageRecordMapper.incReties(messageId);
    }


    @Override
    public void changeStatusByBusiness(MessageRecordStatusDTO messageRecordStatusDTO) {
        if (messageRecordStatusDTO == null) {
            return;
        }
        messageRecordMapper.changeStatusByBusiness(messageRecordStatusDTO);
    }

    @Override
    public List<MessageRecord> getAllNoConsumer(int reties, Date startDate) {
        return messageRecordMapper.findAllByNoConsumer(reties, startDate);
    }
}
