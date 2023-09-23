package com.example.rmq.listener.simple;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 消息消费
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_CLUSTER_GROUP", topic = "SIMPLE_TOPIC")
public class RocketMQSimpleListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("消息消费：{}", message);
    }
}
