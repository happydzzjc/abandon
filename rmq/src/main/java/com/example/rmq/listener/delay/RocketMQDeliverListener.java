package com.example.rmq.listener.delay;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_DELIVER_GROUP", topic = "DELIVER_TOPIC")
public class RocketMQDeliverListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("定时消息: {}", message);
    }
}
