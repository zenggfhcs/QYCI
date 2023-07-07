package com.qyci.controller.user;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.dao.*;
import com.qyci.entity.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user/publish")
public class PublishController {
    @GetMapping("/teach")
    public String teach(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);
        List<CompTeachTag> tags = tagDao.selectAllParents();
        tags.addAll(tagDao.selectAll());
        model.put("tags", tags);

        session.close();
        return "/user/publish/teach";
    }

    @ResponseBody
    @PostMapping("/teach/publish")
    public Object insertTeach(@RequestBody CompTeach teach,HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        if (user == null) {
            return JSON.toJSONString(-1);
        }
        SqlSession session = MySession.getSession();

        CompTeachDao teachDao = session.getMapper(CompTeachDao.class);
        teach.setFrom(user);
        int value = teachDao.insert(teach);

        session.commit();
        session.close();

        if (value == 0) {
            return JSON.toJSONString(0);
        }
        return JSON.toJSONString(teach.getId());
    }

    @ResponseBody
    @PostMapping("/teach/publish-tag")
    public Object insertTeachTagInfo(@RequestBody CompTeachTagsInfo tag, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachTagsInfoDao tagDao = session.getMapper(CompTeachTagsInfoDao.class);

        int value = tagDao.insert(tag);

        if (value == 0) {
            return JSON.toJSONString(0);
        }
        session.commit();
        session.close();
        return JSON.toJSONString(1);
    }

    @GetMapping("/topic")
    public String topic(ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsTagDao tagDao = session.getMapper(TopicsTagDao.class);
        List<TopicsTag> tags = tagDao.selectAll();
        model.put("tags", tags);

        session.close();
        return "/user/publish/topic";
    }

    @ResponseBody
    @PostMapping("/topic/publish")
    public Object insertTopic(@RequestBody Topics topic, HttpServletRequest request, ModelMap model) {
        SqlSession session = MySession.getSession();

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);

        User user = IndexController.getCurrentUser(request);
        if (user == null) {
            return JSON.toJSONString(-1);// 未登录
        }
        topic.setFrom(user);

        int value = topicsDao.insert(topic);

        session.commit();
        session.close();
        if (value == 0) {
            throw new RuntimeException("插入错误");
        }
        return JSON.toJSONString(topic.getId());
    }

    @ResponseBody
    @PostMapping("/topic/publish-tag")
    public Object insertTopicTagInfo(@RequestBody List<Integer> tags, ModelMap model) {
        if (tags.size() == 0) {
            return JSON.toJSONString(0);
        }
        Integer id = tags.get(0);
        tags.remove(0);
        SqlSession session = MySession.getSession();
        TopicsTagsInfoDao tagDao = session.getMapper(TopicsTagsInfoDao.class);
        TopicsTagsInfo tagInfo = MySession.getBean(TopicsTagsInfo.class);
        tagInfo.setTopicsInfo(id);
        for (Integer tag : tags) {
            TopicsTag topicsTag = MySession.getBean(TopicsTag.class);
            topicsTag.setId(tag);
            tagInfo.setTagInfo(topicsTag);
            tagDao.insert(tagInfo);
        }

        session.commit();
        session.close();
        return JSON.toJSONString(1);
    }

    @GetMapping("/comp")
    public String comp(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTagDao tagDao = session.getMapper(CompTagDao.class);
        List<CompTag> tags = tagDao.selectAllParents();
        tags.addAll( tagDao.selectAll());
        model.put("tags", tags);

        session.close();
        return "/user/publish/comp";
    }

    @ResponseBody
    @PostMapping("/comp/publish")
    public Object insertComp(@RequestBody Competition competition,HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        if (user == null) {
            return JSON.toJSONString(-1);
        }
        SqlSession session = MySession.getSession();
        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        competition.setFrom(user);
        int value = compDao.insert(competition);
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        session.commit();
        session.close();
        return JSON.toJSONString(competition.getId());
    }

    @ResponseBody
    @PostMapping("/comp/publish-tag")
    public Object insertCompTag(@RequestBody CompTagsInfo info, ModelMap model) {
        System.out.println(info.getCompId() + " " + info.getTagId());
        SqlSession session = MySession.getSession();
        CompTagsInfoDao tagDao = session.getMapper(CompTagsInfoDao.class);
        int value = tagDao.insert(info);
        if (value == 0) {
            return JSON.toJSONString(0);
        }
        session.commit();
        session.close();
        return JSON.toJSONString(1);
    }
}
