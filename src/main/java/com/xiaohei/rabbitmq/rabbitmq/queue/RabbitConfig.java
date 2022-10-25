package com.xiaohei.rabbitmq.rabbitmq.queue;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:43 下午
 * @Describtion rabbitMq 配置类
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue myQueue(){
        return new Queue("ss007",true);
    }

}
