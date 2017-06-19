package org.zhiqsyr.mfmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.zhiqsyr.mfmall.domain.entity.User;
import org.zhiqsyr.mfmall.service.UserService;

/**
 * @author zhiqsyr
 * @since 2017/6/13
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired private UserService userService;

    /**
     * id、account、psw、mobile、email、status 中哪个或哪些传值，则据其查出唯一结果<br>
     * 可以用于1）登录，2）获取用户信息
     *
     * @param user
     * @return user
     */
    @GetMapping("find/one")
    public Object findOne(User user) {
        return userService.findOne(user);
    }

    /**
     * 注册<br>
     * 1）首先注册用户；2）更新forum中会员数、最新会员信息
     *
     * @param user
     * @return user.id
     */
    @PostMapping("register")
    public Object register(@RequestBody User user) {
        return userService.register(user);
    }

    @PutMapping("modify")
    public Object modify(@RequestBody User user) {
        userService.modify(user);
        return null;
    }

}
