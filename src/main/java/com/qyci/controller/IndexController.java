package com.qyci.controller;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.user.UserController;
import com.qyci.dao.LoginStateDao;
import com.qyci.dao.UserDao;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/")
public class IndexController {
    @GetMapping
    public String index(HttpServletRequest request, ModelMap model) {
        // 检查登录
        User cUser = getCurrentUser(request);
        model.put("user", cUser);
        // 空检查
//        if (cUser == null) {
//            System.out.println("null");
//        }
        // 设置默认内容
        UserController.indexInit(model);
        return "user/index";
    }

    public static User getCurrentUser(HttpServletRequest request) {
        // 获取请求中对应的 ip
        String ip = MySession.getIpAddress(request);
        // 检查 ip 登录 情况
        SqlSession session = MySession.getSession();
        LoginStateDao loginDao = session.getMapper(LoginStateDao.class);
        // 查询 ip 是否存在登录记录
        Integer userId = loginDao.selectIdByUserIp(ip);
        if (userId == null) {// 不存在
//            System.out.println("ip不存在：" + ip);
            return null;
        }
        // 存在
        // 检查登录有效期
        userId = loginDao.selectIdByUserId(userId);
        if (userId == null) {// 已失效
//            System.out.println("登录失效");
            return null;
        }
        // 有效
        UserDao userDao = session.getMapper(UserDao.class);
        User user = userDao.selectByPrimaryKey(userId);

        session.close();
        return user;
    }

    @ResponseBody
    @PostMapping("/login-check")
    public Object check(HttpServletRequest request, ModelMap model) {
        User user = getCurrentUser(request);
        return user == null ?
                JSON.toJSONString(0)// 未登录
                :
                JSON.toJSONString(1);
    }
}
