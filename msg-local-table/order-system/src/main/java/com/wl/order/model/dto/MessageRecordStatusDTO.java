package com.wl.order.model.dto;

import lombok.Data;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 10:47
 */
@Data
public class MessageRecordStatusDTO {
    Long msgId;

    Long businessId;

    String businessType;

    Integer oldStatus;

    Integer newStatus;

}
