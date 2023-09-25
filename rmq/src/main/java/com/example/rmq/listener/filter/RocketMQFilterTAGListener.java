package com.example.rmq.listener.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.SelectorType;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;

/**
 * 消息过滤
 * Tag标签过滤为精准字符串匹配，过滤规则设置格式如下：
 *  1.单Tag匹配：过滤表达式为目标Tag。表示只有消息标签为指定目标Tag的消息符合匹配条件，会被发送给消费者。
 *  2.多Tag匹配：多个Tag之间为或的关系，不同Tag间使用两个竖线（||）隔开。例如，Tag1||Tag2||Tag3，表示标签为Tag1或Tag2或Tag3的消息都满足匹配条件，都会被发送给消费者进行消费。
 *  3.全部匹配：使用星号（*）作为全匹配表达式。表示主题下的所有消息都将被发送给消费者进行消费。
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_FILTER_GROUP", topic = "FILTER_TAG_TOPIC",
        selectorType = SelectorType.TAG, selectorExpression = "tag1")
public class RocketMQFilterTAGListener implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("使用tag过滤的消息，这里只消费tag = tag1 或者 tag2的消息……，message: {}", message);
    }
}
