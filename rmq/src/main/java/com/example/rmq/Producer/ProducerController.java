package com.example.rmq.Producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(path = "producer")
public class ProducerController {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    /**
     * 普通消息 - 广播
     *
     * @return 返回结果
     */
    @RequestMapping("/broad")
    public String broad() {
        rocketMQTemplate.convertAndSend("BROAD_TOPIC:tag1", "这是一条广播消息，将由所有的消费者消费……");
        return "广播消息发送成功！";
    }

    /**
     * 普通消息 - 集群
     *
     * @return 返回结果
     */
    @RequestMapping("/cluster")
    public String cluster() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("KEYS", "123456789");
        rocketMQTemplate.convertAndSend("CLUSTER_TOPIC:tag2", "这是一条集群消息，将由一个天选消费者消费……", map);
        return "集群消息发送成功！";
    }

    /**
     * 同步消息
     * producer 线程阻塞，等待 Broker 返回发送结果
     *
     * @return 返回结果
     */
    @RequestMapping("/sync")
    public String sync() {
        SendResult sendResult = rocketMQTemplate.syncSend("SIMPLE_TOPIC", "这是一条同步消息……");
        log.info("同步消息返回结果：{}", sendResult);
        return "同步消息发送成功！";
    }

    /**
     * 异步消息
     * producer 发送消息线程不阻塞，消息发送结果的回调任务在另一个线程中执行
     *
     * @return 返回结果
     */
    @RequestMapping("/async")
    public String async() {
        rocketMQTemplate.asyncSend("SIMPLE_TOPIC", "这是一条异步消息……", new SendCallback() {
            // 成功回调
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步消息回调：异步消息发送成功！{}", sendResult);
            }

            // 失败回调
            @Override
            public void onException(Throwable throwable) {
                log.info("异步消息回调：异步消息发送失败！{}", throwable.getMessage(), throwable);
            }
        });
        return "异步消息发送成功！";
    }

    /**
     * 单向消息
     *
     * @return 返回结果
     */
    @RequestMapping("/oneWay")
    public String oneWay() {
        rocketMQTemplate.sendOneWay("SIMPLE_TOPIC", "这是一条单向消息，它不关心消息的结果……");
        return "单向消息发送成功！";
    }

    /**
     * 延迟消息
     *
     * @return 操作结果
     */
    @RequestMapping("/delay")
    public String delay() {

        /*
         * 参数1：topic
         * 参数2：消息内容
         * 参数3：毫秒值，相对于现在，多少毫秒后执行
         */
        SendResult sendResult = rocketMQTemplate.syncSendDelayTimeMills("DELAY_TOPIC", "这是一条延迟消息", 10000);
        log.info("延迟消息发送结果：{}", sendResult);

        return "延迟消息发送成功！";
    }

    /**
     * 定时消息
     *
     * @return 操作结果
     */
    @RequestMapping("/deliver")
    public String deliver() {

        /*
         * 参数1：topic
         * 参数2：消息内容
         * 参数3：毫秒值，消费时间的时间戳
         */
        long time = LocalDateTime.now().plusSeconds(10).toInstant(ZoneOffset.of("+8")).toEpochMilli();
        SendResult sendResult = rocketMQTemplate.syncSendDeliverTimeMills("DELIVER_TOPIC", "这是一条定时消息", time);
        log.info("定时消息发送结果：{}", sendResult);


        return "定时消息发送成功！";
    }

    /**
     * 过滤消息
     *
     * @return 操作结果
     */
    @RequestMapping("/filter")
    public String filter() {
        rocketMQTemplate.convertAndSend("FILTER_TAG_TOPIC:tag1", "FILTER_TAG_TOPIC:tag1");
        rocketMQTemplate.convertAndSend("FILTER_TAG_TOPIC:tag2", "FILTER_TAG_TOPIC:tag2");
        rocketMQTemplate.convertAndSend("FILTER_TAG_TOPIC:tag3", "FILTER_TAG_TOPIC:tag3");

        Map<String, Object> map1 = new HashMap<>();
        map1.put("name", "li");
        map1.put("age", 20);
        rocketMQTemplate.convertAndSend("FILTER_SQL92_TOPIC", map1, map1);
        Map<String, Object> map2 = new HashMap<>();
        map2.put("name", "li");
        map2.put("age", 17);
        rocketMQTemplate.convertAndSend("FILTER_SQL92_TOPIC", map2, map2);
        Map<String, Object> map3 = new HashMap<>();
        map3.put("name", "zhang");
        map3.put("age", 30);
        rocketMQTemplate.convertAndSend("FILTER_SQL92_TOPIC", map3, map3);

        return "过滤消息发送成功！";
    }
}
