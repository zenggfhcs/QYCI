package com.qyci.dao;

import com.qyci.entity.CompTeach;
import com.qyci.entity.CompTeachTag;
import com.qyci.entity.CompTeachTagsInfo;

import java.util.List;

public interface CompTeachTagsInfoDao {
    List<CompTeachTagsInfo> selectByTeachKey(Integer key);

    List<Integer> selectByTag(CompTeachTag tag);

    int insert(CompTeachTagsInfo tag);

    void deleteByTagId(Integer id);

    void deleteById(CompTeach teach);
}