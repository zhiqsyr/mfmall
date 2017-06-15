package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zhiqsyr.mfmall.dao.UserRepository;
import org.zhiqsyr.mfmall.domain.entity.User;
import org.zhiqsyr.mfmall.utils.BeanUtils;
import org.zhiqsyr.mfmall.utils.MD5Utils;

import java.util.List;

/**
 * @author zhiqsyr
 * @since 2017/6/13
 */
@Service
@Transactional
public class UserService extends BaseService {

    @Autowired private UserRepository userRepository;

    /**
     * id、account、psw、mobile、email、status 中哪个或哪些传值，则据其查出唯一结果
     *
     * @param user
     * @return
     */
    public User findOne(User user) {
        List<User> result = baseDao.findByExample(User.class, user);
        if (result.size() == 0) return null;
        return result.get(0);
    }

    /**
     * 查询最近注册的用户
     *
     * @return
     */
    public User findLastestRegister() {
        List<User> users = userRepository.findByOrderByIdDesc(new PageRequest(0, 1)).getContent();
        if (users.size() == 0) return null;
        return users.get(0);
    }

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

    /**
     * 修改用户信息（传了值的属性才会修改）
     *
     * @param edit
     */
    public void modify(User edit) {
        Assert.notNull(edit.getId(), "user.id shouldn't be null.");

        User user = userRepository.findOne(edit.getId());
        BeanUtils.copyProperties(edit, user, true);
        userRepository.save(user);
    }

}
