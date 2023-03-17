package com.wl.rk.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MqDedup {
    /**
     * 消费的应用名（可以用消费者组名称）
     */
    private String applicationName;

    /**
     * 消息来源的topic（不同topic消息不会认为重复）
     */
    private String topic;

    /**
     * 消息的tag（同一个topic不同的tag，就算去重键一样也不会认为重复），没有tag则存""字符串
     */
    private String tag;

    /**
     * 消息的唯一键（建议使用业务主键）
     */
    private String msgUniqKey;

    /**
     * 这条消息的消费状态
     */
    private String status;

    /**
     * 这个去重记录的过期时间（时间戳）
     */
    private Long expireTime;
}