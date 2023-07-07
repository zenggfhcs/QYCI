package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.dao.UserDao;
import com.qyci.entity.CompTeach;
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
@RequestMapping("/admin/user-info")
public class UserInfoController {
    @GetMapping("/info")
    public String info(HttpServletRequest request, ModelMap model) {
        SqlSession session = MySession.getSession();
        UserDao userDao = session.getMapper(UserDao.class);
        List<User> userInfo = userDao.selectAllOrdinaryUser();
        model.put("userInfo", userInfo);
        return "/admin/user-info/info-table::content";
    }

    @ResponseBody
    @PostMapping("/resetName")
    public Object resetName(@RequestBody User target, ModelMap model) {
        SqlSession session = MySession.getSession();
        UserDao user = session.getMapper(UserDao.class);
        int value =  user.resetNameById(target);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
    @ResponseBody
    @PostMapping("/resetPi")
    public Object resetPi(@RequestBody User target, ModelMap model) {
        SqlSession session = MySession.getSession();
        UserDao user = session.getMapper(UserDao.class);
        int value =  user.resetPiById(target);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
    @ResponseBody
    @PostMapping("/resetHeadImage")
    public Object resetHeadImage(@RequestBody User target, ModelMap model) {
        System.out.println("id:"+target.getId());
        SqlSession session = MySession.getSession();
        UserDao user = session.getMapper(UserDao.class);
        int value =  user.resetHeadImageById(target);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
    @ResponseBody
    @PostMapping("/updateRole")
    public Object updateRole(@RequestBody User target, ModelMap model) {
        System.out.println("id:"+target.getId());
        SqlSession session = MySession.getSession();
        UserDao user = session.getMapper(UserDao.class);
        int value =  user.updateRoleById(target);
        session.commit();
        session.close();
        if (value == 0)
            return JSON.toJSONString(0);
        return JSON.toJSONString(1);
    }
}
