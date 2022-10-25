package com.xiaohei.rabbitmq.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xiaohei.rabbitmq.entity.ResponseData;
import com.xiaohei.rabbitmq.entity.UserInfo;
import com.xiaohei.rabbitmq.mapper.UserInfoMapper;
import com.xiaohei.rabbitmq.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author liangyusheng@xiaomi.com
 * @Date 2021/11/12 2:27 下午
 * @Version 1.0
 * @Describtion
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;


    @Override
    public ResponseData insertUserInfo(UserInfo userInfo) {
        userInfoMapper.insert(userInfo);
        return new ResponseData(Boolean.TRUE);
    }
}
