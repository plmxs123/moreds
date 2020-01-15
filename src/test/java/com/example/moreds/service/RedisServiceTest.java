package com.example.moreds.service;

import com.alibaba.fastjson.JSONObject;
import com.example.moreds.MoredsApplicationTests;
import com.example.moreds.bean.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by admin on 2020/1/14 0014.
 */
public class RedisServiceTest extends MoredsApplicationTests {

    @Autowired
    private ListOperations listOperations;

    private List<User> list;

    @Before
    public void createList(){
        list = new ArrayList<>();
        User u1 = new User();
        u1.setUserName("hh");
        u1.setCreateTime(new Date());
        u1.setId(1);
        u1.setData("这是测试数据hh");
        list.add(u1);
        User u2 = new User();
        u2.setUserName("xx");
        u2.setCreateTime(new Date());
        u2.setId(2);
        u2.setData("这是测试数据xx");
        list.add(u2);
    }

    @Test
    public void testList1(){
        List<User> range = listOperations.range("testList1", 0, -1);
        System.out.println(range);
    }

    @Test
    public void testList2(){
        listOperations.rightPushAll("testList2",list.stream().map(o -> {
            JSONObject object = new JSONObject();
            object.put("markingTaskDistributeId", o.getId());
            object.put("fkPaperStudentId", o.getUserName());
            object.put("testNumber", o.getId());
            object.put("score", 0.55);
            return object;
        }).collect(Collectors.toList()));
    }

}
