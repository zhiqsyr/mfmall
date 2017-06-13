package org.zhiqsyr.mfmall.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
     * 注册
     *
     * @param user
     * @return
     */
    @PostMapping(value = "register")
    public Object register(@RequestBody User user) {
        return userService.register(user);
    }

}
