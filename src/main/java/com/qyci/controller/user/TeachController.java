package com.qyci.controller.user;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.dao.CompTeachCommentDao;
import com.qyci.dao.CompTeachDao;
import com.qyci.dao.CompTeachTagsInfoDao;
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
@RequestMapping("/user/vs/vs-content")
public class TeachController {
    @GetMapping("/*")
    public String content(HttpServletRequest request, ModelMap model) {
        String path = request.getServletPath();
        String[] paths = path.split("/");
        try {
            // 获取 id
            Integer id = Integer.valueOf(paths[paths.length - 1]);

            SqlSession session = MySession.getSession();
            // 查询 id 对应教学
            CompTeachDao infoDao = session.getMapper(CompTeachDao.class);
            CompTeach info = infoDao.selectByPrimaryKey(id);
            // 教学不存在
            if (info == null) {
                throw new RuntimeException("教学视频不不存在");
            }
            // 存在
            model.put("info", info);

            // 查询教学的标签
            CompTeachTagsInfoDao tagDao = session.getMapper(CompTeachTagsInfoDao.class);
            List<CompTeachTagsInfo> tags = tagDao.selectByTeachKey(id);
            model.put("tags", tags);

            // 查询教学下的评论
            CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
            List<CompTeachComment> comments = commentDao.selectByTeachKey(info.getId());
            model.put("comments", comments);

            session.close();

            // 设置登录信息
            User user = IndexController.getCurrentUser(request);
            model.put("user", user);

        } catch (Exception e) {
            return "/user/error";
        }

        return "/user/vs/vs-content";
    }

    @ResponseBody
    @PostMapping("/submit-comment")
    public Object submit(@RequestBody CompTeachComment comment,HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        if (user == null) {
            return JSON.toJSONString(-1);
        }

        SqlSession session = MySession.getSession();
        CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
        comment.setFrom(user);
        int value = commentDao.insert(comment);
        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }


    @ResponseBody
    @PostMapping("/delete-comment")
    public Object delete(@RequestBody CompTeachComment comment, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachCommentDao commentDao = session.getMapper(CompTeachCommentDao.class);
        long res = commentDao.updateByKey(comment.getId());
        session.commit();
        session.close();
        return JSON.toJSONString(res);
    }
}
