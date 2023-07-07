package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.dao.CompTeachCommentDao;
import com.qyci.dao.TopicsCommentDao;
import com.qyci.entity.CompTeachComment;
import com.qyci.entity.TopicsComment;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/admin/comment-mgt")
public class MgtCommentController {
    @GetMapping("/teach")
    public String cTableTeach(ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
        List<CompTeachComment> comments = commentDao.selectAll();
        model.put("comments", comments);

        session.close();

        return "/admin/comment/ctable-teach::content";
    }

    @GetMapping("/topic")
    public String cTableTopic(ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsCommentDao commentDao = session.getMapper(TopicsCommentDao.class);
        List<TopicsComment> comments = commentDao.selectAll();
        model.put("comments", comments);

        session.close();
        return "/admin/comment/ctable-topic::content";
    }

    @ResponseBody
    @PostMapping("/teach/delete-comment")
    public Object deleteTeachComment(@RequestBody CompTeachComment comment, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
        int value = commentDao.updateByComment(comment);

        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }

    @ResponseBody
    @PostMapping("/topic/delete-comment")
    public Object deleteTopicComment(@RequestBody TopicsComment comment, ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsCommentDao commentDao = session.getMapper(TopicsCommentDao.class);
        int value = commentDao.updateByComment(comment);

        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
}
