package com.wl.order.service;

import com.wl.order.model.dto.MessageRecordStatusDTO;
import com.wl.order.model.entity.MessageRecord;

import java.util.Date;
import java.util.List;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 10:54
 */
public interface MessageRecordService {

    void save(MessageRecord messageRecord);

    void changeStatusByMessageId(long messageId, int oldStatus, int newStatus);

    void incReties(long messageId);

    void changeStatusByBusiness(MessageRecordStatusDTO messageRecordStatusDTO);


    List<MessageRecord> getAllNoConsumer(int reties, Date startDate);
}
