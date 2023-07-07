package com.qyci.controller.user.login;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.dao.LoginStateDao;
import com.qyci.dao.UserDao;
import com.qyci.entity.Condition;
import com.qyci.entity.LoginState;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/login/register")
public class RegisterController {

    @GetMapping
    public String register(ModelMap model) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user = context.getBean(User.class);
        model.put("user", user);
        return "login/register";
    }

    @ResponseBody
    @PostMapping("/insert")
    public Object insert(@RequestBody User user, HttpServletRequest request, ModelMap model) {
        //
        SqlSession session = MySession.getSession();

        UserDao ud = session.getMapper(UserDao.class);
        //
        ud.insert(user);
        if (user.getId() == 0) {
            return JSON.toJSONString(0);
        }

        LoginStateDao loginDao = session.getMapper(LoginStateDao.class);

        LoginState state = MySession.getBean(LoginState.class);
        state.setUserId(user.getId());
        state.setUserIp(MySession.getIpAddress(request));

        loginDao.deleteById_Ip(state);
        loginDao.insert(state);

        session.commit();
        session.close();
        //
        return JSON.toJSONString(user.getId());

    }

    @ResponseBody
    @PostMapping("/checking")
    public Object checkCode(ModelMap model, @RequestBody Condition condition) {// 验证码检查
        Condition mCondition = MySession.getBean(Condition.class);
        if (condition.getCheck().equals(mCondition.getCheck())) {
            model.put("condition", mCondition);

            return JSON.toJSONString(1);
        }
        return JSON.toJSONString(0);
    }

    @ResponseBody
    @PostMapping("/search")
    public Object search(@RequestBody User user) {// 验证码检查
        SqlSession session = MySession.getSession();
        UserDao ud = session.getMapper(UserDao.class);
        int value = ud.checkContains(user);
        session.close();
        if (value > 1) {
            return JSON.toJSONString(-1);
        }
        if (value == 1) {
            return JSON.toJSONString(1);
        }
        return JSON.toJSONString(0);
    }
}
