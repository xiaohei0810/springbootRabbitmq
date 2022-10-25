package com.xiaohei.rabbitmq.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @description (user_info)表实体类
 * @author 梁羽生
 * @date 2021-11-08 18:04:34
 */
 
@TableName(value = "user_info")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfo {

    @TableId
    private Long uuid;

    private String username;
    
    private String password;


}