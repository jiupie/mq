package com.wl.order.mapper;


import com.wl.order.model.dto.MessageRecordStatusDTO;
import com.wl.order.model.entity.MessageRecord;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author yangy
 * @description 针对表【message_record(本地消息记录表)】的数据库操作Mapper
 * @createDate 2023-08-04 10:40:50
 * @Entity com.wl.order.model.entity.MessageRecord
 */
public interface MessageRecordMapper {
    void insert(MessageRecord messageRecord);

    MessageRecord findByBusinessIdAndBusinessType(String businessId, String businessType);

    void changeStatusByMessageId(@Param("messageId") long messageId, @Param("oldStatus") int oldStatus, @Param("newStatus") int newStatus);

    void changeStatusByBusiness(MessageRecordStatusDTO messageRecordStatusDTO);

    List<MessageRecord> findAllByNoConsumer(@Param("reties") int reties,@Param("startDate") Date startDate,@Param("numbers") int numbers);

    void incReties(@Param("messageId") long messageId);
}




