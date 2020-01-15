package com.example.moreds.service;

import com.example.moreds.bean.User;
import com.example.moreds.config.SourceTarget;
import com.example.moreds.dao.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 *
 * @author admin
 * @date 2020/1/13 0013
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    @SourceTarget(value = "mysql2")
    public User selectOne(){
        User one = userMapper.findOne();
        return null;
    }

    @SourceTarget(value = "mysql1")
    public User selectOne1(){
        User one = userMapper.findOne();
        return null;
    }
}
