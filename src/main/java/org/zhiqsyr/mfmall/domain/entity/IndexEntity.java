package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * @author zhiqsyr
 * @since 2017/6/19
 */
@Data
@MappedSuperclass
public class IndexEntity {

    @Column(name = "`index`")
    private Double index;      // 顺序索引

}
