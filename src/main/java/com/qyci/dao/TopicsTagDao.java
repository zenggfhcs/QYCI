package com.qyci.dao;

import com.qyci.entity.TopicsTag;

import java.util.List;

public interface TopicsTagDao {

    List<TopicsTag> selectAll();

    int deleteByPrimaryKey(Integer id);

    int insert(TopicsTag record);

    int insertSelective(TopicsTag record);

    TopicsTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TopicsTag record);

    int updateByPrimaryKey(TopicsTag record);
}