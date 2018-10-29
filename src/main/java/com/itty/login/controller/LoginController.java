package com.itty.login.controller;

import com.itty.common.utils.MD5Utils;
import com.itty.user.dao.UserMapper;
import com.itty.user.entity.User;
import com.itty.user.service.UserService;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;


@Controller
@RequestMapping("login")
public class LoginController {
    @Autowired
    private UserService userService;

    /**
     * @return java.lang.String
     * @Author hezefei
     * @Description 登陆页面
     * @Date 22:12 2018/8/29
     * @Param []
     **/
    @RequestMapping
    public String login() {

        return "login";
    }

    /**
     * @return java.lang.String
     * @Author hezefei
     * @Description 登陆验证
     * @Date 19:11 2018/8/31
     * @Param [request]
     **/
    @RequestMapping("/check")
    @ResponseBody
    public String checkLogin(HttpServletRequest request) throws Exception {
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");

        UsernamePasswordToken token = new UsernamePasswordToken(username, pwd);
//        token.setRememberMe(true);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            SecurityUtils.getSubject().getSession().setTimeout(1800000);
        } catch (Exception e) {
            return "login_fail";
        }
        return "login_succ";

    }


}
