package com.wl.rk.consumer;

import com.wl.rk.entity.MqDedup;
import com.wl.rk.mapper.MqDedupMapper;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/21
 */
@Component
@RocketMQMessageListener(consumerGroup = "simple-cg-boot", topic = "simple-tp-boot")
public class SimpleConsumer implements RocketMQListener<MessageExt> {

    @Autowired
    private MqDedupMapper mqDedupMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void onMessage(MessageExt messageExt) {

        MqDedup mqDedup = new MqDedup();
        mqDedup.setStatus("0");
        mqDedup.setApplicationName("test");
        String msgContent = new String(messageExt.getBody());
        mqDedup.setMsgUniqKey(msgContent);
        mqDedup.setTopic(messageExt.getTopic());
        mqDedup.setTag("");
        mqDedup.setExpireTime(System.currentTimeMillis());
        mqDedupMapper.insert(mqDedup);
        System.out.println(messageExt);

        //业务处理
//        if ("test".equals(msgContent)) {
//            throw new RuntimeException("消费错误");
//        }
    }
}
