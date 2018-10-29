package com.itty.attend.controller;

import com.itty.attend.entity.Attend;
import com.itty.attend.service.AttendService;
import com.itty.attend.vo.QueryCondition;
import com.itty.common.page.PageQueryBean;
import com.itty.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Auther: hezefei
 * @Date: 2018/9/4 20:01
 * @Description:
 */
@Controller
@RequestMapping("attend")
public class AttendController {
    @Autowired
    private AttendService attendService;

    @RequestMapping
    public String toAttend() {
        return "attend";
    }

    /**
     * @return java.lang.String
     * @Author hezefei
     * @Description 签到
     * @Date 9:02 2018/9/13
     * @Param [attend]
     **/
    @RequestMapping("/sign")
    @ResponseBody
    public String signAttend(@RequestBody Attend attend) throws Exception {
        attendService.signAttend(attend);
        return "suscc";
    }

    /**
     * @return com.itty.common.page.PageQueryBean
     * @Author hezefei
     * @Description 考勤数据分页查询
     * @Date 9:04 2018/9/13
     * @Param [condition, session]
     **/
    @RequestMapping("/attendList")
    @ResponseBody
    public PageQueryBean listAttend(QueryCondition condition, HttpSession session) {
        User user = (User) session.getAttribute("userinfo");
        String[] rangeDate = condition.getRangeDate().split("/");
        condition.setStartDate(rangeDate[0]);
        condition.setEndDate(rangeDate[1]);
        condition.setUserId(user.getId());
        PageQueryBean result = attendService.listAttend(condition);
        return result;
    }
}
