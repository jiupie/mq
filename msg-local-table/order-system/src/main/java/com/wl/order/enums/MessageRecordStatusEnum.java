package com.wl.order.enums;

import lombok.Getter;

/**
 * @author 南顾北衫
 * @email 17674641491@163.com
 * @date 2023/8/4 10:58
 */
@Getter
public enum MessageRecordStatusEnum {
    NOT_SEND(0), SEND_SUCCESS(1), CONSUMER_SUCCESS(2),
    ;
    private final int code;

    MessageRecordStatusEnum(int code) {
        this.code = code;
    }
}
