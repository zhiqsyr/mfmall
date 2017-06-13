package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zhiqsyr.mfmall.dao.UserRepository;
import org.zhiqsyr.mfmall.domain.entity.User;
import org.zhiqsyr.mfmall.utils.MD5Utils;

/**
 * @author zhiqsyr
 * @since 2017/6/13
 */
@Service
@Transactional
public class UserService {

    @Autowired private UserRepository userRepository;

    /**
     * 注册
     *
     * @param user
     * @return id
     */
    public Integer register(User user) {
        Assert.noNullElements(new Object[]{user.getAccount(), user.getPsw(), user.getName(), user.getEmail()}, "Some properties shouldn't be null.");

        user.setPsw(MD5Utils.encode32(user.getPsw()));

        userRepository.save(user);
        return user.getId();
    }

}
