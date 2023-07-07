package com.qyci.controller.admin;

import com.alibaba.fastjson.JSON;
import com.qyci.MySession;
import com.qyci.controller.IndexController;
import com.qyci.entity.Condition;
import com.qyci.entity.MyDate;
import com.qyci.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @ResponseBody
    @PostMapping("/check")
    public Object check(HttpServletRequest request, ModelMap model) {
        Condition condition = MySession.getBean(Condition.class);
        User user = IndexController.getCurrentUser(request);
        if (user != null && user.getRole() == 7) {
            return JSON.toJSONString(true);
        }
        return JSON.toJSONString(false);
    }
    @GetMapping()
    public String admin(ModelMap model) {
        return "admin/index";
    }

    @GetMapping("/index")
    public String index(ModelMap model) {
        return admin(model);
    }


    @GetMapping("/review")
    public String review(ModelMap model) {
        return "admin/review";
    }


    @GetMapping("/tag-mgt")
    public String tagManagement(ModelMap model) {
        return "admin/tag-mgt";
    }


    @GetMapping("/comment-mgt")
    public String commentManagement(ModelMap model) {
        return "admin/comment-mgt";
    }


    @GetMapping("/user-info-mgt")
    public String userInfoManagement(ModelMap model) {
        return "admin/user-info-mgt";
    }
    @GetMapping("/content-mgt")
    public String contentManagement(ModelMap model) {
        return "admin/content-mgt";
    }


}