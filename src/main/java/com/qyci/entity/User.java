package com.qyci.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * user
 *
 * @author ztc
 */
@Data
public class User implements Serializable {
    public void replace(User user) {
        id = user.getId();
        password = "";
        role = user.getRole();
        email = user.getEmail();
        name = user.getName();
        sex = user.getSex();
        age = user.getAge();
        headImage = user.getHeadImage();
        birthday = user.getBirthday();
        personalIntroduction = user.getPersonalIntroduction();
    }

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 密码
     */
    private String password;

    /**
     * 角色
     */
    private Byte role;

    /**
     * email用户标识
     */
    private String email;

    private String name;

    private Object sex;

    private Byte age;

    /**
     * 头像链接
     */
    private String headImage;

    private Date birthday;

    /**
     * 个人描述
     */
    private String personalIntroduction;

    private static final long serialVersionUID = 1L;
}