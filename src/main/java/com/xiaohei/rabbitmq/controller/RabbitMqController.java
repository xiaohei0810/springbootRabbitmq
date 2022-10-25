package com.xiaohei.rabbitmq.controller;

import com.xiaohei.rabbitmq.rabbitmq.sender.QueueSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2022/10/24 5:52 下午
 * @Describtion 消息发送
 */
@RestController
@RequestMapping("/rabbitMq")
public class RabbitMqController {

    @Autowired
    private QueueSender queueSender;

    /**
     * 默认交换机和路由方式发送消息
     * @param msg
     * @return
     */
    @GetMapping("/send")
    public String sendMegToMq(@RequestParam String msg) {
        queueSender.sendMessage(msg);
        return "ok";
    }

    /**
     * 指定交换机和路由发送消息
     * @param msg
     */
    @GetMapping("/sendToExchange")
    public void sendMegToExchange(@RequestParam String msg,@RequestParam String route) {
        queueSender.sendMessageToExchange(msg,route);
    }

}
