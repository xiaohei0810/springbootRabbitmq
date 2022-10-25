package com.xiaohei.rabbitmq.rabbitmq.receiver;

import com.xiaohei.rabbitmq.entity.UserInfo;
import com.xiaohei.rabbitmq.mapper.UserInfoMapper;
import com.xiaohei.rabbitmq.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:36 下午
 * @Describtion 消息队列消费者
 */
@Slf4j
@Component
public class QueueConsumer {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @RabbitListener(queues = "test")
    public void receive1(String message) {
        System.out.println("消费test队列的数据:{}" + message);
    }

    @RabbitListener(queues = "ss007")
    public void receive2(String message) {
        UserInfo build = UserInfo.builder().username("小黑2啊")
//                .uuid(4212)
                .password("message").build();
        userInfoService.insertUserInfo(build);

        System.out.println("消费ss007队列的数据:{}" + message);
    }

    /**
     * 消费者 消费队列中的消息
     * @param message
     */
    @RabbitListener(queues = "topicQueue1")
    public void receive3(String message) {

        System.out.println("消费topic1队列的数据:{}" + message);
    }

    @RabbitListener(queues = "topicQueue2")
    public void receive4(String message) {

        System.out.println("消费topic2队列的数据:{}" + message);
    }

}
