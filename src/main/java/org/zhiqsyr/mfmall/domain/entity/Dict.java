package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 数据字典
 *
 * @author zhiqsyr
 * @since 17/4/18
 */
@Data
@Entity
@Table(name = "dict")
@DynamicInsert
@DynamicUpdate
public class Dict {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String type;    // 字典类型：10000=版块类型
    private String name;    // 名称
    private Integer index;
    private Integer userId;
    private Date createdTime;

}
