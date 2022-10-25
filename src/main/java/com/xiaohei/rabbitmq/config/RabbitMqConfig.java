package com.xiaohei.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:43 下午
 * @Describtion rabbitMq 配置类
 */
@Configuration
@Slf4j
public class RabbitMqConfig {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 监听消息回吊，如果消息丢失异常，日志记录。
     */
    @PostConstruct
    public void enableConfirmCallback() {
        //confirm 监听，当消息成功发到交换机 ack = true，没有发送到交换机 ack = false
        //correlationData 可在发送时指定消息唯一 id
        // 消息如果没能发送到Exchange，调用此方法。
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.info("correlationData:{}", correlationData);
                log.info("ack:{}", ack);
                log.info("cause:{}", cause);
                //记录日志、发送邮件通知、落库定时任务扫描重发
            }
        });

        //当消息成功发送到交换机的，但是没有路由到队列触发此监听
        rabbitTemplate.setReturnsCallback(returned -> {
            //记录日志、发送邮件通知、落库定时任务扫描重发
            log.info("returned:{}", returned);
        });

    }


    /**
     * 不指定交换机Exchange和队列Queue，则用默认的direct直连模式的Exchange，路由默认位队列名称。
     *
     * @return
     */
    @Bean
    public Queue myQueue() {
        return new Queue("ss007", true);
    }

    @Bean
    public Queue topicQueue1() {
        return new Queue("topicQueue1", Boolean.TRUE);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue("topicQueue2", Boolean.TRUE);
    }

    @Bean
    public Queue directQueue1() {
        return new Queue("directQueue1", Boolean.TRUE);
    }

    @Bean
    public DirectExchange directExchange(){
        return new DirectExchange("directExchange");
    }


    /**
     * 指定主题交换机模式： TopicExchange
     *
     * @return
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("topicExchange");
    }

    /**
     * 交换机和队列绑定，接受路由s007.id的消息
     *
     * @return
     */
    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("ss007.id.*");
    }

    /**
     * 一个交换机绑定两个队列，routingkey不一样
     *
     * @return
     */
    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("ss007.*");
    }

    @Bean
    public Binding directBinding1(){
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("direct.route.key");
    }

}
