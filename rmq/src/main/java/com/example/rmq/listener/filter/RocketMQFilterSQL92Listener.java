package com.example.rmq.listener.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 消息过滤
 * Tag是一种系统属性，所以SQL过滤方式也兼容Tag标签过滤。在SQL语法中，Tag的属性名称为TAGS。
 *
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_FILTER_GROUP", topic = "FILTER_SQL92_TOPIC",
        selectorType = SelectorType.SQL92, selectorExpression = "age > 18")
public class RocketMQFilterSQL92Listener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("使用SQL92过滤的消息，这里只消费 age大于18 的消息…… message = {}", message);
    }
}
