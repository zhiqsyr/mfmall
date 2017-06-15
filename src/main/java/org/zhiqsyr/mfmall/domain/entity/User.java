package org.zhiqsyr.mfmall.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.zhiqsyr.mfmall.domain.enums.Status;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "user")
@DynamicInsert
@DynamicUpdate
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String account;
    private String psw;

    private String name;
    @Enumerated(EnumType.STRING)
    private Sex sex;
    private String mobile;
    private String email;
    private String sign;

    @Enumerated(EnumType.STRING)
    private Role role;      // 角色：MEMBER=网站会员
    @Enumerated(EnumType.STRING)
    private Status status;  // 帐号状态
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdTime;

    public enum Sex { M, F }
    public enum Role { ADMIN, MEMBER }

}
