package com.xiaohei.rabbitmq.rabbitmq.receiver;

import com.rabbitmq.client.Channel;
import com.xiaohei.rabbitmq.entity.UserInfo;
import com.xiaohei.rabbitmq.mapper.UserInfoMapper;
import com.xiaohei.rabbitmq.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
     *
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


    /**
     * 如果生产环境你用上述方案的代码，一旦发生一次消费报错你就会崩溃。因为 basicNack 方法的第三个参数代表是否重回队列，如果你填 false 那么消息就直接丢弃了，相当于没有保障消息可靠。如果你填 true ，当发生消费报错之后，这个消息会被重回消息队列顶端，继续推送到消费端，继续消费这条消息，通常代码的报错并不会因为重试就能解决，所以这个消息将会出现这种情况：继续被消费，继续报错，重回队列，继续被消费......死循环
     * 所以真实的场景一般是三种选择
     *
     * 当消费失败后将此消息存到 Redis，记录消费次数，如果消费了三次还是失败，就丢弃掉消息，记录日志落库保存
     * 直接填 false ，不重回队列，记录日志、发送邮件等待开发手动处理
     * 不启用手动 ack ，使用 SpringBoot 提供的消息重试
     *
     * SpringBoot 提供的消息重试
     * 其实很多场景并不是一定要启用消费者应答模式，因为 SpringBoot 给我们提供了一种重试机制，当消费者执行的业务方法报错时会重试执行消费者业务方法。
     *
     * 链接：https://juejin.cn/post/6977981645475282958
     * @param object
     * @param message
     * @param channel
     */
    @RabbitListener(queues = "directQueue1")
    private void directReceive(String object, Message message, Channel channel) {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        log.info("消费成功：{},消息内容:{}", deliveryTag, object);
        try {
            /**
             * 执行业务代码...
             * */
            // 手动确认机制。
            channel.basicAck(deliveryTag, true);
        } catch (Exception e) {
            log.error("签收失败", e);
            try {
                channel.basicNack(deliveryTag, false, true);
            } catch (IOException exception) {
                log.error("拒签失败", exception);
            }
//            throw new RuntimeException("消息消费失败");
        }
    }

}
