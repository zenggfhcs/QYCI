package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.dao.*;
import com.qyci.entity.CompTeach;
import com.qyci.entity.Competition;
import com.qyci.entity.Topics;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/content-mgt")
public class MgtContentController {
    @GetMapping("/mgt-comp")
    public String comp(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        List<Competition> competitions = compDao.mgt_selectAll();
        model.put("competitions", competitions);

        session.close();
        return "admin/content-mgt/mgt-comp::content";
    }

    @GetMapping("/mgt-teach")
    public String teach(ModelMap model) {
        SqlSession session = MySession.getSession();

        CompTeachDao teachDao = session.getMapper(CompTeachDao.class);
        List<CompTeach> teaches = teachDao.mgt_selectAll();
        System.out.println(teaches.size());
        model.put("teaches", teaches);

        session.close();
        return "admin/content-mgt/mgt-teach::content";
    }

    @GetMapping("/mgt-topic")
    public String topic(ModelMap model) {
        SqlSession session = MySession.getSession();

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);
        List<Topics> topics = topicsDao.mgt_selectAll();
        model.put("topics", topics);

        session.close();
        return "admin/content-mgt/mgt-topic::content";
    }

    @ResponseBody
    @PostMapping("/mgt-teach/mgt-delete")
    public Object deleteTeach(@RequestBody CompTeach teach, ModelMap model) {
        SqlSession session = MySession.getSession();
//        先删标签
        CompTeachTagsInfoDao infoDao = session.getMapper(CompTeachTagsInfoDao.class);
        infoDao.deleteById(teach);

//        改评论不可见
        CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
        commentDao.updateById(teach);

//        后删文件
        CompTeachDao TeachDao = session.getMapper(CompTeachDao.class);
        int value = TeachDao.disVisitById(teach);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/mgt-comp/mgt-delete")
    public Object deleteComp(@RequestBody CompTeach comp, ModelMap model) {
        SqlSession session = MySession.getSession();
//        先删标签
        CompTagsInfoDao infoDao = session.getMapper(CompTagsInfoDao.class);
        infoDao.deleteById(comp);
//        后删文件
        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        int value = compDao.disVisitById(comp);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/mgt-topic/mgt-delete")
    public Object deleteTopic(@RequestBody Topics topic, ModelMap model) {
        SqlSession session = MySession.getSession();
//        先删标签
        TopicsTagsInfoDao infoDao = session.getMapper(TopicsTagsInfoDao.class);
        infoDao.deleteById(topic);
//        改评论不可见
        TopicsCommentDao commentDao = session.getMapper(TopicsCommentDao.class);
        commentDao.updateById(topic);
//        后删文件
        TopicsDao compDao = session.getMapper(TopicsDao.class);
        int value = compDao.disVisitById(topic);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
}
