package com.qyci.controller.user.login;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.dao.LoginStateDao;
import com.qyci.dao.UserDao;
import com.qyci.entity.Condition;
import com.qyci.entity.LoginState;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String login(ModelMap model) {
        User user = MySession.getBean(User.class);
        model.put("user", user);
        return "login/index";
    }

    @GetMapping("/")
    public String login_(ModelMap model) {
        return login(model);
    }

    @GetMapping("/index")
    public String loginIndex(ModelMap model) {
        return login(model);
    }

    @ResponseBody
    @PostMapping("/login")
    public Object loginAction(@RequestBody User user, HttpServletRequest request, ModelMap model) {
        SqlSession session = MySession.getSession();
        UserDao ud = session.getMapper(UserDao.class);
        User have = ud.selectByPrimaryKey(user.getId());
        if (have == null || have.getId() == null) {
            // 不存在的账号
            return JSON.toJSONString(-1);
        }

        Byte value = ud.selectRole(user.getId());
        if (Objects.isNull(value)) {
            // 密码错误
            return JSON.toJSONString(0);
        }

        LoginStateDao loginDao = session.getMapper(LoginStateDao.class);
        LoginState state = MySession.getBean(LoginState.class);
        state.setUserId(have.getId());
        state.setUserIp(MySession.getIpAddress(request));

        loginDao.deleteById_Ip(state);
        loginDao.insert(state);

        switch (value) {
            // 普通用户登录
            // 初始化操作
            case 17: {
                Condition condition = MySession.getBean(Condition.class);
                condition.setLogged(1);
                model.put("condition", condition);
                User mUser = MySession.getBean(User.class);
                mUser.replace(have);
                // reset
                break;
            }
            // 管理员登录
            // 初始化操作
            case 7: {
                break;
            }
            // 初始化操作
            default: {

            }
        }

        session.commit();
        session.close();
        return JSON.toJSONString(value);
    }


}
