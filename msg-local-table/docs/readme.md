# 本地消息表保证最终一致性
```sql
CREATE TABLE `message_record` (
            `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
            `business_id` varchar(64) DEFAULT NULL COMMENT '业务数据ID',
            `business_type` int DEFAULT NULL COMMENT '业务类型：具体业务',
            `retries_number` int DEFAULT 0 COMMENT '重试次数',
            `msg_status` int DEFAULT 0 COMMENT '结果 1 成功  0 失败',
            `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='本地消息记录表';

```


状态：
 1.生产者已投递
 2.消费系统已消费
 3.生产者发布失败
 4.通知队列发送成功
 5.通知队列发送失败