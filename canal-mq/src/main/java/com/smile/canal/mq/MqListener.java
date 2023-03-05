package com.smile.canal.mq;

import com.alibaba.fastjson.JSONObject;
import com.smile.canal.es.domain.CanalBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.ConsumeMode;
import org.apache.rocketmq.spring.annotation.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * @Description
 * @ClassName MqListener
 * @Author smile
 * @date 2023.03.05 18:49
 */
@Slf4j
@Component
@RocketMQMessageListener(
        topic = "cms-topic",
        consumerGroup = "cms-group",
        selectorExpression = "*",
        consumeMode = ConsumeMode.CONCURRENTLY,
        messageModel = MessageModel.CLUSTERING,
        consumeThreadMax = 10
)
public class MqListener implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        CanalBean canalBean = JSONObject.parseObject(message, CanalBean.class);
        log.info("收到消息:{}", canalBean);
    }
}
