```sql
CREATE TABLE `t_rocketmq_dedup` (
`application_name` varchar(255) NOT NULL COMMENT '消费的应用名（可以用消费者组名称）',
`topic` varchar(255) NOT NULL COMMENT '消息来源的topic（不同topic消息不会认为重复）',
`tag` varchar(16) NOT NULL COMMENT '消息的tag（同一个topic不同的tag，就算去重键一样也不会认为重复），没有tag则存""字符串',
`msg_uniq_key` varchar(255) NOT NULL COMMENT '消息的唯一键（建议使用业务主键）',
`status` varchar(16) NOT NULL COMMENT '这条消息的消费状态',
`expire_time` bigint(20) NOT NULL COMMENT '这个去重记录的过期时间（时间戳）',
UNIQUE KEY `uniq_key` (`application_name`,`topic`,`tag`,`msg_uniq_key`) USING BTREE
)
```
