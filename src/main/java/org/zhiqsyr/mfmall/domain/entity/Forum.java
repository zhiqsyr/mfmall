package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 论坛统计信息
 *
 */
@Data
@Entity
@Table(name = "forum")
@DynamicInsert
@DynamicUpdate
public class Forum {

    @Id
    private Integer id;

    private Long visits;    // 访问量

    private Integer topics;     // 主题数
    private Integer postsToday;     // 今日帖数，即时累加，定时清0
    private Integer postsYesterday; // 昨日帖数，定时更新
    private Integer posts;          // 帖子数
    private Integer users;          // 会员数（user.size）

    private Integer newestUserId;   // 最新会员ID
    private String newestUserName;  // 最新会员名称

    private String newestPost;      // 最新回复信息，用于展示与跳转

}
