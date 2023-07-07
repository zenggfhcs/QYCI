package com.qyci.dao;

import com.qyci.entity.CompTagsInfo;
import com.qyci.entity.CompTeach;
import com.qyci.entity.CompTeachTag;

import java.util.List;

public interface CompTagsInfoDao {
    List<Integer> selectByTag(CompTeachTag tag);

    int insert(CompTagsInfo info);

    void deleteByTagId(Integer id);

    void deleteById(CompTeach comp);
}