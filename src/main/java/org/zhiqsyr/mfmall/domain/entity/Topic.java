package org.zhiqsyr.mfmall.domain.entity;

import io.swagger.models.auth.In;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 主题
 *
 */
@Data
@Entity
@Table(name = "topic")
@DynamicInsert
@DynamicUpdate
public class Topic {

    @Id
    private Integer id;

    private String title;       // 标题
    private String content;     // 内容

    private Integer visits;         // 查看次数
    private Date lastVisitedTime;   // 最后查看时间
    private Integer posts;          // 回复数量

    private Integer index;          // 顺序索引，用于置顶等

    private Integer userId;
    private String userName;
    private Date createdTime;
    private Date lastModifiedTime;

}
