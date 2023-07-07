package com.qyci.dao;

import com.qyci.entity.Topics;
import com.qyci.entity.TopicsTag;
import com.qyci.entity.TopicsTagsInfo;

import java.util.List;

public interface TopicsTagsInfoDao {

    List<TopicsTag> selectByTopicsKey(Integer id);

    void insert(TopicsTagsInfo tagInfo);

    void deleteById(Topics topic);
}