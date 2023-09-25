package com.example.rmq.listener.delay;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_DELAY_GROUP", topic = "DELAY_TOPIC")
public class RocketMQDelayListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("延时消费: {}", message);
    }
}
