package com.qyci.dao;

import com.qyci.entity.User;

import java.util.List;

public interface UserDao {
    int deleteByPrimaryKey(Integer id);

    User selectByPrimaryKey(Integer id);


    int updateByPrimaryKey(User record);

    Byte selectRole(Integer id);

    int insert(User user);

    int checkContains(User user);

    User selectByEmail(String email);

    int updatePassword(User user);

    int updateByUser(User user);

    List<User> selectAllOrdinaryUser();

    int resetNameById(User target);

    int resetPiById(User target);

    int resetHeadImageById(User target);

    int updateRoleById(User target);
}