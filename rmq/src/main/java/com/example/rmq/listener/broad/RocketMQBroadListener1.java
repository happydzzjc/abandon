package com.example.rmq.listener.broad;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.springframework.stereotype.Service;

/**
 * 广播消息
 * 每个消费者，都会消费消息
 * RocketMQMessageListener
 *  - messageModel:MessageModel.BROADCASTING 代表广播模式
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_BROAD_GROUP", topic = "BROAD_TOPIC", messageModel = MessageModel.BROADCASTING)
public class RocketMQBroadListener1 implements RocketMQListener<String>, RocketMQPushConsumerLifecycleListener {
    @Override
    public void onMessage(String message) {
        log.info("消费者 >>>一号<<< 处理当前消息：{}", message);
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer consumer) {
        consumer.setInstanceName("RocketMQBroadListener1");
    }
}
