package com.itty.user.controller;

import com.itty.user.entity.User;
import com.itty.user.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/home")
    public String home() {


        return "home";
    }

    /**
     * @return com.itty.user.entity.User
     * @Author hezefei
     * @Description 获取用户信息
     * @Date 19:10 2018/8/31
     * @Param [session]
     **/
    @RequestMapping("userinfo")
    @ResponseBody
    public User userinfo(HttpSession session) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("userinfo");
        return user;
    }

    /**
     * @return java.lang.String
     * @Author hezefei
     * @Description 退出系统
     * @Date 16:43 2018/9/14
     * @Param [session]
     **/
    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "login";
    }

}
