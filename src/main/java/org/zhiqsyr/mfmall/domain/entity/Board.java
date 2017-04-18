package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zhiqsyr.mfmall.domain.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * 版块
 *
 */
@Data
@Entity
@Table(name = "board")
@DynamicInsert
@DynamicUpdate
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;    // 名称
    private String brief;   // 简介

    private Integer categoryId; // 分类

    private Integer topics;     // 主题数
    private Integer posts;      // 帖子数

    private Integer index;      // 顺序索引

    private Integer userId;     // 创建人ID
    private String userName;    // 创建人名称
    private Date createdTime;   // 创建时间

}
