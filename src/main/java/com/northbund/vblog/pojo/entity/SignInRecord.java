package com.northbund.vblog.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="sign_in_record")
public class SignInRecord  {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户id
     */
    @Column(name="user_id")
    protected Long userId;

    /**
     * 签到日期
     */
    @Column(name="sign_in_date")
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date signInDate;
}
