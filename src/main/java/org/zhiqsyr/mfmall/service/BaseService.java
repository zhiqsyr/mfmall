package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.zhiqsyr.mfmall.dao.BaseDao;

/**
 * @author zhiqsyr
 * @since 2017/6/14
 */
public class BaseService {

    @Autowired protected BaseDao baseDao;

}
