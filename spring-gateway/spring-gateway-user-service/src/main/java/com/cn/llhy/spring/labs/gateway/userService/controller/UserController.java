package com.cn.llhy.spring.labs.gateway.userService.controller;

import com.cn.llhy.spring.labs.gateway.userService.po.UserAddPO;
import com.cn.llhy.spring.labs.gateway.userService.po.UserPO;
import org.springframework.web.bind.annotation.*;

/**
 * @author Huangyong
 * @version 1.0.0
 * @Description
 * @createTime 2022-3-22 14:46
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/get")
    public UserPO getUser(@RequestParam("id") Integer id){
        UserPO userPO= new UserPO();
        userPO .setId(id);
        userPO .setName("没有昵称：" + id);
        userPO .setGender(id % 2 + 1); // 1 - 男；2 - 女
        return userPO;
    }

    @PostMapping("/add")
    public Integer add(UserAddPO addPO) {
        return (int) (System.currentTimeMillis() / 1000); // 随便返回一个 id
    }

}
