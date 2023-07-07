package com.qyci.dao;

import com.qyci.entity.CompTeach;
import com.qyci.entity.Competition;
import com.qyci.entity.User;

import java.util.List;

public interface CompetitionDao {
    User selectUserByKey(Integer id);

    List<Competition> selectByKeys(List<Integer> ids);

    int insert(Competition competition);

    Competition selectByPrimaryKey(Integer id);

    List<Competition> selectFirst();

    List<Competition> selectByComp(Competition competition);

    List<Competition> selectAllByComp(Competition competition);

    int updateByState(Competition competition);

    int disVisitById(CompTeach comp);

    List<Competition> mgt_selectAll();
}