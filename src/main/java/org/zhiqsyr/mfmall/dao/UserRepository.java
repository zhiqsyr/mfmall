package org.zhiqsyr.mfmall.dao;

import org.springframework.data.repository.CrudRepository;
import org.zhiqsyr.mfmall.domain.entity.User;

/**
 * @author zhiqsyr
 * @since 2017/6/13
 */
public interface UserRepository extends CrudRepository<User, Integer> {

}
