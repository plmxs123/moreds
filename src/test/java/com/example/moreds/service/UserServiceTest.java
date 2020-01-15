package com.example.moreds.service;

import com.example.moreds.MoredsApplicationTests;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by admin on 2020/1/13 0013.
 */

public class UserServiceTest extends MoredsApplicationTests {

    @Autowired
    private UserService userService;

    @Test
    public void selectOne() throws Exception {
        System.out.println(userService.selectOne());
        System.out.println(userService.selectOne1());
    }

}
