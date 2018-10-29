package com.itty.common.task;

import com.itty.attend.service.AttendService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Auther: hezefei
 * @Date: 2018/9/13 08:24
 * @Description:
 */
public class AttendCheckTask {
    @Autowired
    private AttendService attendService;

    public void checkAttend() {

        attendService.checkAttend();

    }
}
