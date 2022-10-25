package com.xiaohei.rabbitmq.service;

import com.xiaohei.rabbitmq.entity.ResponseData;
import com.xiaohei.rabbitmq.entity.UserInfo;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2021/11/12 2:27 下午
 * @Version 1.0
 * @Describtion
 */
public interface UserInfoService {

    ResponseData insertUserInfo(UserInfo userInfo);
}
