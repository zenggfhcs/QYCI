package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.dao.CompTeachDao;
import com.qyci.dao.CompetitionDao;
import com.qyci.dao.TopicsDao;
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
@RequestMapping("/admin/review")
public class ReviewController {
    @GetMapping("/content-comp")
    public String comp(ModelMap model) {
        SqlSession session = MySession.getSession();

        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        Competition competition = MySession.getBean(Competition.class);
        competition.setReview(false);
        competition.setReviewResult(false);
        List<Competition> competitions = compDao.selectAllByComp(competition);
        model.put("competitions", competitions);

        session.close();
        return "admin/review/content-comp::content";
    }

    @GetMapping("/content-teach")
    public String teach(ModelMap model) {
        SqlSession session = MySession.getSession();

        CompTeachDao teachDao = session.getMapper(CompTeachDao.class);

        CompTeach teach = MySession.getBean(CompTeach.class);
        teach.setReview(false);
        teach.setReviewResult(false);
        List<CompTeach> teaches = teachDao.selectAllByTeach(teach);
        System.out.println(teaches.size());
        model.put("teaches", teaches);

        session.close();
        return "admin/review/content-teach::content";
    }

    @GetMapping("/content-topic")
    public String topic(ModelMap model) {
        SqlSession session = MySession.getSession();

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);

        Topics topic = MySession.getBean(Topics.class);
        topic.setReview(false);
        topic.setReviewResult(false);
        List<Topics> topics = topicsDao.selectAllByTopic(topic);
        model.put("topics", topics);

        session.close();
        return "admin/review/content-topic::content";
    }

    @ResponseBody
    @PostMapping("/content-topic/update-state")
    public Object updateTopic(@RequestBody Topics topics, ModelMap model) {
        SqlSession session = MySession.getSession();
        TopicsDao topicsDao = session.getMapper(TopicsDao.class);
        int value = topicsDao.updateByState(topics);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1 );
    }
    @ResponseBody
    @PostMapping("/content-comp/update-state")
    public Object updateComp(@RequestBody Competition competition, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompetitionDao competitionDao = session.getMapper(CompetitionDao.class);
        int value = competitionDao.updateByState(competition);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1 );
    }
    @ResponseBody
    @PostMapping("/content-teach/update-state")
    public Object updateTeach(@RequestBody CompTeach teach, ModelMap model) {
        SqlSession session = MySession.getSession();
        CompTeachDao TeachDao = session.getMapper(CompTeachDao.class);
        int value = TeachDao.updateByState(teach);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1 );
    }
}
