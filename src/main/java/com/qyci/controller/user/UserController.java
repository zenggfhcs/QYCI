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
@RequestMapping("/user")
public class UserController {
    public static void indexInit(ModelMap model) {
        // 首页 视频教学部分
        SqlSession session = MySession.getSession();

        CompTeachDao comInfo = session.getMapper(CompTeachDao.class);
        // 查询希望展示的部分，使用 topping 控制
        List<CompTeach> teaches = comInfo.selectFirst();
        model.put("teaches", teaches);

        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        List<Competition> competitions = compDao.selectFirst();
        model.put("competitions", competitions);

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);
        List<Topics> topics = topicsDao.selectFirst();
        model.put("topics", topics);

        session.close();
    }

    @GetMapping("/")
    public String user(HttpServletRequest request, ModelMap model) {
        // 检查登录情况
        User cUser = IndexController.getCurrentUser(request);
        model.put("user", cUser);
        // 内容初始化
        indexInit(model);
        return "user/index";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request, ModelMap model) {
        return user(request, model);
    }

    @GetMapping("/vs-teach")
    public String teach(HttpServletRequest request, ModelMap model) {
        // 检查登录情况
        User cUser = IndexController.getCurrentUser(request);
        model.put("user", cUser);

        SqlSession session = MySession.getSession();

        // 内容初始化
        CompTeachTagDao tagDao = session.getMapper(CompTeachTagDao.class);
        List<CompTeachTag> parents = tagDao.selectAllParents();
        model.put("parents", parents);

        List<CompTeachTag> tags = tagDao.selectAll();
        model.put("tags", tags);

        session.close();

        return "user/vs-teach";
    }


    @GetMapping("/vs-teach/teach")
    public String getTeach(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "parents", required = false) Integer parents, ModelMap model) {
        // 设置 tag 信息
        CompTeachTag tag = MySession.getBean(CompTeachTag.class);
        tag.setId(id);
        tag.setParents(parents);
        // 查询 tag 相对应的 teach
        SqlSession session = MySession.getSession();

        CompTeachTagsInfoDao infoDao = session.getMapper(CompTeachTagsInfoDao.class);
        List<Integer> ids = infoDao.selectByTag(tag);
        System.out.println(ids);

        CompTeachDao teachDao = session.getMapper(CompTeachDao.class);
        List<CompTeach> teaches = teachDao.selectByKeys(ids);
        model.put("teaches", teaches);

        session.close();
        return "user/vs-teach::content";
    }


    @GetMapping("/vs")
    public String vs(HttpServletRequest request, ModelMap model) {
        // 检查登录情况
        User cUser = IndexController.getCurrentUser(request);
        model.put("user", cUser);
        // 初始化内容
        SqlSession session = MySession.getSession();

        CompTagDao tagDao = session.getMapper(CompTagDao.class);
        List<CompTag> parents = tagDao.selectAllParents();
        model.put("parents", parents);

        List<CompTag> tags = tagDao.selectAll();
        model.put("tags", tags);

        session.close();
        return "user/vs";
    }

    @GetMapping("/vs/comp")
    public String getComp(@RequestParam(value = "id", required = false) Integer id, @RequestParam(value = "parents", required = false) Integer parents, ModelMap model) {
        CompTeachTag tag = MySession.getBean(CompTeachTag.class);
        System.out.println(id + " " + parents);
        tag.setId(id);
        tag.setParents(parents);
        SqlSession session = MySession.getSession();

        CompTagsInfoDao infoDao = session.getMapper(CompTagsInfoDao.class);
        List<Integer> ids = infoDao.selectByTag(tag);
        System.out.println(ids);

        CompetitionDao compDao = session.getMapper(CompetitionDao.class);
        List<Competition> competitions = compDao.selectByKeys(ids);
        model.put("competitions", competitions);

        session.close();
        return "user/vs::vs-content";
    }

    @GetMapping("/community")
    public String community(HttpServletRequest request, ModelMap model) {
        // 检查登录情况
        User cUser = IndexController.getCurrentUser(request);
        model.put("user", cUser);
        // 初始化内容
        SqlSession session = MySession.getSession();

        TopicsDao topicsDao = session.getMapper(TopicsDao.class);
        List<Topics> topTopics = topicsDao.selectTop();
        model.put("topTopics", topTopics);

        List<Topics> allTopics = topicsDao.selectAll();
        model.put("allTopics", allTopics);

        session.close();
        return "user/community";
    }

    @ResponseBody
    @PostMapping("/exit")
    public Object exit(HttpServletRequest request, ModelMap model) {
        String ip = MySession.getIpAddress(request);

        SqlSession session = MySession.getSession();

        LoginStateDao loginDao = session.getMapper(LoginStateDao.class);
        int value = loginDao.exitByIp(ip);

        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }

}
