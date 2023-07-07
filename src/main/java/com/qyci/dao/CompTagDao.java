package com.qyci.dao;

import com.qyci.entity.CompTag;

import java.util.List;

public interface CompTagDao {
    int deleteByPrimaryKey(Integer id);

    int insert(CompTag record);

    int insertSelective(CompTag record);

    CompTag selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CompTag record);

    int updateByPrimaryKey(CompTag record);

    List<CompTag> selectAll();

    List<CompTag> selectAllParents();

    int updateNameByKey(CompTag tag);

    int selectParentsCount();

    int updateParents(CompTag tag);

    int selectSubsetCount(CompTag tag);


    int deleteByParents(CompTag tag);
}