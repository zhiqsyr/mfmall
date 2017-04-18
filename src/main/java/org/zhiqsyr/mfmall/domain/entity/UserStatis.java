package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zhiqsyr.mfmall.domain.enums.Status;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户统计数据
 *
 */
@Data
@Entity
@Table(name = "user_statis")
@DynamicInsert
@DynamicUpdate
public class UserStatis {

    @Id
    private Integer userId;

    private Integer integral;   // 积分
    private Short level;        // 级别

    private Integer topics;     // 主题数量
    private Integer posts;      // 帖子数量

}
