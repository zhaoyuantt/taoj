package com.landasoft.taoj;

import com.landasoft.taoj.pojo.TUserInfo;
import com.landasoft.taoj.service.AdminUserService;
import com.landasoft.taoj.utils.MyResult;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUser {

    @Autowired
    private AdminUserService adminUserService;

    @Test
    public void testInsertAdminUser(){
        TUserInfo userInfo = new TUserInfo();
        userInfo.setUsername("sfy");
        userInfo.setPasswd("sfy@sfy.com");
        userInfo.setPhoneNumber("15105165555");
        userInfo.setEmail("sfy@land.com");

        MyResult myResult = adminUserService.registerAdminUser(userInfo);
        System.out.println(myResult.getStatus());
    }

    @Test
    public void testCheckLogin(){
        TUserInfo userInfo = new TUserInfo();
        userInfo.setUsername("sfy");
        userInfo.setPasswd("sfy@sfy.com");

        MyResult myResult = adminUserService.checkLogin(userInfo);
        System.out.println(myResult.getStatus());
    }


}
