package org.zhiqsyr.mfmall.service;

import com.google.common.collect.ImmutableList;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.zhiqsyr.mfmall.dao.BaseDao;
import org.zhiqsyr.mfmall.domain.dto.Order;
import org.zhiqsyr.mfmall.domain.dto.Page;
import org.zhiqsyr.mfmall.domain.entity.IndexEntity;

import java.util.List;

/**
 * @author zhiqsyr
 * @since 2017/6/14
 */
@Transactional
public class BaseService {

    @Autowired protected BaseDao baseDao;

    /**
     * 排序：交换 index
     *
     * @param clazz
     * @param id
     * @param withId
     */
    public <T extends IndexEntity> void exchangeIndex(Class<T> clazz, Integer id, Integer withId) {
        IndexEntity idVo = baseDao.get(clazz, id);
        IndexEntity withIdVo = baseDao.get(clazz, withId);

        Double idVoIndex = idVo.getIndex();
        idVo.setIndex(withIdVo.getIndex());
        withIdVo.setIndex(idVoIndex);
        baseDao.updateAll(ImmutableList.of(idVo, withIdVo));
    }

    /**
     * 排序：拖拽排序
     *
     * @param clazz
     * @param id        被拖拽的一条ID
     * @param betweenId 被拖拽到betweenId和andId之间
     * @param andId
     * @param <T>
     */
    public <T extends IndexEntity> void drag(Class<T> clazz, Integer id, Integer betweenId, Integer andId) {
        IndexEntity idVo = baseDao.get(clazz, id);
        IndexEntity betweenIdVo = baseDao.get(clazz, betweenId);
        IndexEntity andIdVo = baseDao.get(clazz, andId);

        idVo.setIndex((betweenIdVo.getIndex() + andIdVo.getIndex()) / 2);
        baseDao.update(idVo);
    }

    /**
     * 排序：置顶
     *
     * @param clazz
     * @param id
     * @param <T>
     */
    public <T extends IndexEntity> void top(Class<T> clazz, Integer id) {
        Page page = new Page(0, 1, ImmutableList.of(new Order(Sort.Direction.ASC, "index")));
        List<Object> list = baseDao.findByDetachedCriteria(DetachedCriteria.forClass(clazz), page).getContent();

        IndexEntity vo = baseDao.get(clazz, id);
        if (list.size() == 0) vo.setIndex(-10D);
        else vo.setIndex(((IndexEntity) list.get(0)).getIndex() - 10);
        baseDao.update(vo);
    }

}
