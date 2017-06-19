package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zhiqsyr.mfmall.dao.ForumRepository;
import org.zhiqsyr.mfmall.domain.entity.Forum;
import org.zhiqsyr.mfmall.domain.entity.User;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@Service
@Transactional
public class ForumService extends BaseService {

    @Autowired private ForumRepository forumRepository;

    /**
     * 获取论坛信息
     *
     * @return
     */
    public Forum get() {
        return forumRepository.findOne(100);
    }

    /**
     * 注册用户之后，更新会员数、最新会员信息
     *
     * @param user
     */
    public void afterRegisterUser(User user) {
        Forum forum = get();

        forum.setUsers(forum.getUsers() + 1);
        forum.setNewestUserId(user.getId());
        forum.setNewestUserName(user.getName());
        forumRepository.save(forum);
    }

}
