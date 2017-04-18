package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 热门版块
 *
 */
@Data
@Entity
@Table(name = "board_hot")
@DynamicInsert
@DynamicUpdate
public class BoardHot {

    @Id
    private Integer boardId;

    private Integer index;      // 顺序索引

    private Integer userId;     // 创建人ID
    private Date createdTime;   // 创建时间

}
