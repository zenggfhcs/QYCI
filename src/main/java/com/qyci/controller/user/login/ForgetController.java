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
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/login/forget")
public class ForgetController {
    /**
     * 忘记密码-输入邮箱视图(根目录处理)
     *
     * @param model 上下文对象
     * @return forget-input-email view
     */
    @GetMapping
    public String index(ModelMap model) {
        User user = MySession.getBean(User.class);
        model.put("user", user);
        Condition condition = MySession.getBean(Condition.class);
        model.put("condition", condition);
        return "login/forget";
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

    @ResponseBody
    @PostMapping("/checking")
    public Object checkCode(ModelMap model, @RequestBody Condition condition) {// 验证码检查
        Condition mCondition = MySession.getBean(Condition.class);
        if (condition.getCheck().equals(mCondition.getCheck())) {
            return JSON.toJSONString(1);
        }
        return JSON.toJSONString(0);
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updatePassword(@RequestBody User user, HttpServletRequest request, ModelMap model) {
        SqlSession session = MySession.getSession();
        UserDao ud = session.getMapper(UserDao.class);
        // 更新密码
        int value = ud.updatePassword(user);
        session.commit();
        if (value == 0) {// 更新失败
            return JSON.toJSONString(-1);
        }
        // 检查异常
//        System.out.println(user.getEmail());
        User have = ud.selectByEmail(user.getEmail());
        System.out.println(have.getHeadImage());
        if (have.getId() == null) {// 异常
            return JSON.toJSONString(0);
        }
        // 更新信息

        LoginStateDao loginDao = session.getMapper(LoginStateDao.class);
        LoginState state = MySession.getBean(LoginState.class);
        state.setUserId(have.getId());
        state.setUserIp(MySession.getIpAddress(request));

        loginDao.deleteById_Ip(state);
        loginDao.insert(state);

        session.close();

        return JSON.toJSONString((int) have.getRole());//
    }
}

