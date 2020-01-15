package com.example.moreds.dao;

import com.example.moreds.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 * @author admin
 * @date 2020/1/13 0013
 */
@Mapper
public interface UserMapper {
    User findOne();
}
