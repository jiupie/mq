package com.wl.order.model.entity;

import lombok.Data;

import java.util.Date;

/**
 * 本地消息记录表
 *
 * @TableName message_record
 */
@Data
public class MessageRecord {
    /**
     * 主键ID
     */
    private Long id;

    /**
     * 业务数据ID
     */
    private String businessId;

    /**
     * 业务类型：具体业务
     */
    private String businessType;

    /**
     * 主题 topic
     */
    private String topic;

    /**
     * 重试次数
     */
    private Integer retriesNumber;

    /**
     * 结果  0:未发送  1:发送成功  2:消费成功
     */
    private Integer msgStatus;

    /**
     * 消息内容
     */
    private String msgText;

    /**
     * 创建时间
     */
    private Date createTime;

}