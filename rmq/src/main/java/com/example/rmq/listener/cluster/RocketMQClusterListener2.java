package com.example.rmq.listener.cluster;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Service;


/**
 * 负载均衡消费
 * 每一个消息只会被某一个消费者消费一次
 * RocketMQMessageListener
 * - consumerGroup 完全相同的订阅和consumerGroup，才能正确实现负载均衡
 * - messageModel:MessageModel.CLUSTERING 代表集群模式
 */
@Slf4j
@Service
@RocketMQMessageListener(consumerGroup = "ABANDON_RMQ_CLUSTER_GROUP", topic = "CLUSTER_TOPIC")
public class RocketMQClusterListener2 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("普通消息，由消费者二号消费：{}", message);
    }

}

