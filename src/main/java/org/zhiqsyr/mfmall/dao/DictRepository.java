package org.zhiqsyr.mfmall.dao;

import org.springframework.data.repository.CrudRepository;
import org.zhiqsyr.mfmall.domain.entity.Dict;

/**
 * @author zhiqsyr
 * @since 2017/6/13
 */
public interface DictRepository extends CrudRepository<Dict, Integer> {

    Dict findTopByTypeOrderByIdDesc(String type);

}
