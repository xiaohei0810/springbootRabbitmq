package com.xiaohei.rabbitmq.rabbitmq.queue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:43 下午
 * @Describtion rabbitMq 配置类
 */
@Configuration
public class RabbitConfig {

    /**
     * 不指定交换机Exchange和队列Queue，则用默认的direct直连模式的Exchange，路由默认位队列名称。
     * @return
     */
    @Bean
    public Queue myQueue(){
        return new Queue("ss007",true);
    }

    @Bean
    public Queue topicQueue1(){
        return new Queue("topicQueue1",Boolean.TRUE);
    }

    @Bean
    public Queue topicQueue2(){
        return new Queue("topicQueue2",Boolean.TRUE);
    }

    /**
     * 指定交换机Exchange
     * @return
     */
    @Bean
    public TopicExchange topicExchange(){
        return new TopicExchange("topicExchange");
    }

    /**
     * 交换机和队列绑定，接受路由s007.id的消息
     * @return
     */
    @Bean
    public Binding topicBinding1(){
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("ss007.id.*");
    }

    /**
     * 一个交换机绑定两个队列，routingkey不一样
     * @return
     */
    @Bean
    public Binding topicBinding2(){
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("ss007.*");
    }

}
