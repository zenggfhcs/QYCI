package com.qyci.controller.user;

import com.qyci.MySession;
import com.qyci.dao.CompetitionDao;
import com.qyci.entity.Competition;
import com.qyci.entity.Condition;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/user/comp/comp-content")
public class CompController {
    @GetMapping("/*")
    public String comp(HttpServletRequest request, ModelMap model) {
        String path = request.getServletPath();
        String[] paths = path.split("/");
        try {
            // 获取id
            Integer id = Integer.valueOf(paths[paths.length - 1]);

            SqlSession session = MySession.getSession();
            CompetitionDao infoDao = session.getMapper(CompetitionDao.class);
            Competition info = infoDao.selectByPrimaryKey(id);
            System.out.println(id);

            if (info == null) {
                throw new RuntimeException("话题不存在");
            }
            //
            model.put("info", info);

            session.close();

            Condition condition = MySession.getBean(Condition.class);
            model.put("condition", condition);

            User user = MySession.getBean(User.class);
            model.put("user", user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return "/user/comp/comp-content";
    }
}
