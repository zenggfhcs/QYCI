package com.qyci.dao;

import com.qyci.entity.CompTeach;
import com.qyci.entity.CompTeachTag;

import java.util.List;

public interface CompTeachTagDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CompTeachTag record);

    int insertSelective(CompTeachTag record);

    CompTeachTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompTeachTag record);

    int updateByPrimaryKey(CompTeachTag record);

    List<CompTeachTag> selectAll();

    List<CompTeachTag> selectAllParents();

    int selectParentsCount();

    int selectSubsetCount(CompTeachTag tag);

    int updateParents(CompTeachTag tag);

    int updateNameByKey(CompTeachTag tag);


    int deleteByParents(CompTeachTag tag);

}