package com.wl.produce;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/20
 */
public class MyTransactionListener implements TransactionListener {

    private final Logger logger = LoggerFactory.getLogger(MyTransactionListener.class);

    /**
     * 执行本地事务
     * <p>
     * TransactionStatus.RollbackTransaction: 回滚事务，它代表该消息将被删除，不允许被消费。
     * TransactionStatus.Unknown: 中间状态，它代表需要检查消息队列来确定状态。
     * TransactionStatus.CommitTransaction: 提交事务，它允许消费者消费此消息。
     *
     * @param msg /
     * @param arg /
     * @return /
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
        try {
            logger.info("执行本地事务,订单号为:{}", msg.getKeys());

            //插入订单表
            //插入订单明细表
            //提交事务
            return LocalTransactionState.COMMIT_MESSAGE;
        } catch (Exception e) {
            logger.error("执行本地事务异常,订单号为:{}", msg.getKeys());
            //事务回滚
            return LocalTransactionState.ROLLBACK_MESSAGE;
        }
    }

    /**
     * 回查事务
     *
     * @param msg 消息
     * @return /
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt msg) {
        logger.info("回查事务,订单号为:{}", msg.getKeys());
        //根据订单号查询订单表
        //如果订单表有数据，返回COMMIT_MESSAGE
        //如果订单表没有数据，返回ROLLBACK_MESSAGE
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
