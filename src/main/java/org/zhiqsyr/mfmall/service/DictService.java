package org.zhiqsyr.mfmall.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.zhiqsyr.mfmall.dao.DictRepository;
import org.zhiqsyr.mfmall.domain.entity.Dict;
import org.zhiqsyr.mfmall.utils.BeanUtils;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@Service
@Transactional
public class DictService extends BaseService {

    @Autowired private DictRepository dictRepository;

    /**
     * 添加数据字典
     *
     * @param dict
     * @param type 字典类型：10000=版块类型
     * @return
     */
    public Dict create(Dict dict, String type) {
        Dict maxDict = dictRepository.findTopByTypeOrderByIdDesc(type);

        dict.setId(maxDict == null ? Integer.parseInt(type) : maxDict.getId() + 10);// 同一类型下id+10
        dict.setType(type);
        if (dict.getIndex() == null) dict.setIndex(dict.getId().doubleValue());     // index不传默认=id
        dictRepository.save(dict);

        return dict;
    }

    /**
     * 修改数据字典
     *
     * @param edit
     */
    public void modify(Dict edit) {
        Assert.notNull(edit.getId(), "dict.id shouldn't be null.");

        Dict dict = dictRepository.findOne(edit.getId());
        BeanUtils.copyProperties(edit, dict, true);
        dictRepository.save(dict);
    }

}
