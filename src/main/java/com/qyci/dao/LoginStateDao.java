package com.qyci.dao;

import com.qyci.entity.LoginState;

public interface LoginStateDao {

    Integer selectIdByUserIp(String ip);

    Integer selectIdByUserId(Integer userId);


    int deleteById_Ip(LoginState state);

    int insert(LoginState state);

    int exitByIp(String ip);
}