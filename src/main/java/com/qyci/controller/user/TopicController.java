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
@RequestMapping("/user/com/topic-content")
public class TopicController {
    @GetMapping("/*")
    public String topic(HttpServletRequest request, ModelMap model) {
        String path = request.getServletPath();
        String[] paths = path.split("/");
        try {
            // 获取id
            Integer id = Integer.valueOf(paths[paths.length - 1]);
            SqlSession session = MySession.getSession();
            TopicsDao infoDao = session.getMapper(TopicsDao.class);
            Topics info = infoDao.selectByPrimaryKey(id);
            System.out.println(id);

            if (info == null) {
                throw new RuntimeException("话题不存在");
            }
            //
            model.put("info", info);

            TopicsCommentDao commentDao = session.getMapper(TopicsCommentDao.class);
            List<TopicsComment> comments = commentDao.selectByTopicsKey(info.getId());
            model.put("comments", comments);

            TopicsTagsInfoDao tagDao = session.getMapper(TopicsTagsInfoDao.class);
            List<TopicsTag> tags = tagDao.selectByTopicsKey(id);
            model.put("tags", tags);

            session.close();

            Condition condition = MySession.getBean(Condition.class);
            model.put("condition", condition);

            User user = MySession.getBean(User.class);
            model.put("user", user);
        } catch (Exception e) {
            throw new RuntimeException(e);
            // return "/user/error";
        }

        return "/user/com/topic-content";
    }

    @ResponseBody
    @PostMapping("/submit-comment")
    public Object comment(@RequestBody TopicsComment comment,HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        if (user == null) {
            return JSON.toJSONString(-1);
        }
        SqlSession session = MySession.getSession();

        TopicsCommentDao commentDao = session.getMapper(TopicsCommentDao.class);
        comment.setFrom(user);

        int value = commentDao.insert(comment);

        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }
}
