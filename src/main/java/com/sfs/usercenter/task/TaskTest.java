package com.sfs.usercenter.task;

import com.sfs.usercenter.model.domain.User;
import com.sfs.usercenter.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component

public class TaskTest {
    @Resource
    private UserService userService;

//    @Scheduled(cron = "0 34 19 * * *")
//    public void insert(){
//        System.out.println("成功了");
//    }
}
