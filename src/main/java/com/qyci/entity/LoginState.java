package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * login_state
 * @author 
 */
@Data
public class LoginState implements Serializable {
    private Integer id;

    /**
     * 登录id
     */
    private Integer userId;

    /**
     * 登录ip
     */
    private String userIp;

    /**
     * 登录有效期
     */
    private Date validityPeriod;

    private String token;

    private static final long serialVersionUID = 1L;
}