package com.qyci.controller.user;

import com.alibaba.fastjson2.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.dao.CompTeachDao;
import com.qyci.dao.CompetitionDao;
import com.qyci.dao.TopicsDao;
import com.qyci.dao.UserDao;
import com.qyci.entity.CompTeach;
import com.qyci.entity.Competition;
import com.qyci.entity.Topics;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@Controller
@RequestMapping("/user/info")
public class InfoController {

    @GetMapping
    public String info(HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        model.put("user", user);
        return "/user/info/user-info";
    }

    @GetMapping("/content-comp")
    public String comp(@RequestParam Boolean review, @RequestParam Boolean reviewResult, HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);

        SqlSession session = MySession.getSession();

        CompetitionDao compDao = session.getMapper(CompetitionDao.class);

        Competition competition = MySession.getBean(Competition.class);
        competition.setReview(review);
        competition.setReviewResult(reviewResult);
        competition.setFrom(user);
        List<Competition> competitions = compDao.selectByComp(competition);
        model.put("competitions", competitions);

        session.close();
        return "user/info/content-comp::content";
    }

    @GetMapping("/content-teach")
    public String teach(@RequestParam Boolean review, @RequestParam Boolean reviewResult, HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);

        SqlSession session = MySession.getSession();

        CompTeachDao teachDao = session.getMapper(CompTeachDao.class);

        CompTeach teach = MySession.getBean(CompTeach.class);
        teach.setReview(review);
        teach.setReviewResult(reviewResult);
        teach.setFrom(user);
        List<CompTeach> teaches = teachDao.selectByTeach(teach);
        System.out.println(teaches.size());
        model.put("teaches", teaches);

        session.close();
        return "user/info/content-teach::content";
    }

    @GetMapping("/content-topic")
    public String topic(@RequestParam Boolean review, @RequestParam Boolean reviewResult, HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);

        SqlSession session = MySession.getSession();

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);

        Topics topic = MySession.getBean(Topics.class);
        topic.setReview(review);
        topic.setReviewResult(reviewResult);
        topic.setFrom(user);
        List<Topics> topics = topicsDao.selectByTopic(topic);
        model.put("topics", topics);

        session.close();
        return "user/info/content-topic::content";
    }

    @GetMapping("/info-alter")
    public String alter(HttpServletRequest request, ModelMap model) {
        User user = IndexController.getCurrentUser(request);
        model.put("user", user);
        return "user/info/info-alter";
    }

    @ResponseBody
    @PostMapping("/info-alter")
    public Object alter(@RequestBody User user, HttpServletRequest request, ModelMap model) {
        User cUser = IndexController.getCurrentUser(request);
        if (cUser != null) {
            user.setId(cUser.getId());
        }

        SqlSession session = MySession.getSession();
        UserDao userDao = session.getMapper(UserDao.class);
        int value = userDao.updateByUser(user);
        if (value == 0) {
            return JSON.toJSONString(0);
        }

        session.commit();
        session.close();
        return JSON.toJSONString(1);
    }
}
