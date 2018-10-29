package com.itty.attend.service;

import com.itty.attend.entity.Attend;
import com.itty.attend.vo.QueryCondition;
import com.itty.common.page.PageQueryBean;

public interface AttendService {
    void signAttend(Attend attend) throws Exception;

    PageQueryBean listAttend(QueryCondition condition);

    void checkAttend();
}
