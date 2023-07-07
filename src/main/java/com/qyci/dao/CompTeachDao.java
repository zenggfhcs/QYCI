package com.qyci.dao;

import com.qyci.entity.CompTeach;
import com.qyci.entity.User;

import java.util.List;

public interface CompTeachDao {
    /**
     * association 查询 from
     * @param id 查询依据
     * @return from（user）
     */
    User selectUserByKey(Integer id);


    /**
     * 首页教学查询
     * @return 查询结果 list
     */
    List<CompTeach> selectFirst();

    /**
     * 查询 id 属于 ids 中的 记录，用于标签筛选的功能部分
     * @param ids id list
     * @return 查询结果
     */
    List<CompTeach> selectByKeys(List<Integer> ids);

    CompTeach selectByPrimaryKey(Integer key);

    int insert(CompTeach teach);

    List<CompTeach> selectByTeach(CompTeach teach);

    List<CompTeach> selectAllByTeach(CompTeach teach);

    int updateByState(CompTeach teach);

    List<CompTeach> mgt_selectAll();

    int disVisitById(CompTeach teach);
}