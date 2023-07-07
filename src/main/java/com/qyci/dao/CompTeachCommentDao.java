package com.qyci.dao;

import com.qyci.entity.CompTeach;
import com.qyci.entity.CompTeachComment;
import com.qyci.entity.User;

import java.util.List;

public interface CompTeachCommentDao {
    int updateByKey(Long key);

    User selectUserByKey(Integer key);

    List<CompTeachComment> selectByTeachKey(Integer key);

    int insert(CompTeachComment comment);

    List<CompTeachComment> selectAll();

    int updateByComment(CompTeachComment comment);

    void updateById(CompTeach teach);
}