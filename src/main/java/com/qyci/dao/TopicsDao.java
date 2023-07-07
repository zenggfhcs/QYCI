package com.qyci.dao;

import com.qyci.entity.Topics;
import com.qyci.entity.TopicsTag;

import java.util.List;

public interface TopicsDao {
    List<TopicsTag> selectTagsByKey(Integer id);

    List<Topics> selectTop();

    List<Topics> selectAll();

    int insert(Topics topics);

    Topics selectByPrimaryKey(Integer id);

    List<Topics> selectFirst();

    List<Topics> selectByTopic(Topics topic);

    List<Topics> selectAllByTopic(Topics topic);

    int updateByState(Topics topics);

    List<Topics> mgt_selectAll();

    int disVisitById(Topics topic);
}