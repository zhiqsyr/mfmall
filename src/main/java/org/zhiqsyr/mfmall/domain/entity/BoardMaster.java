package org.zhiqsyr.mfmall.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * 版主
 *
 */
@Data
@Entity
@Table(name = "board_master")
@DynamicInsert
@DynamicUpdate
public class BoardMaster {

    @Id
    private Integer boardId;

    private String master;  // 版主信息 -- {"10000000":"PRIMARY","10000001":"VICE"}

    private Integer userId;     // 创建人ID
    private Date createdTime;   // 创建时间

}
