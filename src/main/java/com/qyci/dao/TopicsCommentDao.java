package com.qyci.dao;

import com.qyci.entity.Topics;
import com.qyci.entity.TopicsComment;
import com.qyci.entity.User;

import java.util.List;

public interface TopicsCommentDao {
    User selectUserByKey(Integer key);

    List<TopicsComment> selectByTopicsKey(Integer id);

    int insert(TopicsComment comment);

    List<TopicsComment> selectAll();

    int updateByComment(TopicsComment comment);

    void updateById(Topics topic);
}