package com.xiaohei.rabbitmq.rabbitmq.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:44 下午
 * @Describtion 消息发送者
 */
@Component
@RequiredArgsConstructor
public class QueueSender {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    private Queue myQueue;

    @Resource
    private AmqpTemplate amqpTemplate;

    public void sendMessage(String message){
        rabbitTemplate.convertAndSend(myQueue.getName(),message);
    }

    public void sendMessageToExchange(String msg, String route) {
        amqpTemplate.convertAndSend("topicExchange",route,msg);
    }
}
