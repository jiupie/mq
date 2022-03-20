package com.wl.produce;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author 南顾北衫
 * @description
 * @date 2022/3/20
 */
public class MyTransactionListener implements TransactionListener {
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
        if ("tagA".equals(msg.getTags())) {
            return LocalTransactionState.UNKNOW;
        } else if ("tagB".equals(msg.getTags())) {
            return LocalTransactionState.COMMIT_MESSAGE;
        } else {
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

        return LocalTransactionState.UNKNOW;
    }
}
