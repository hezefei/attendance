package com.itty.workflow.sereice;

import com.itty.workflow.entity.ReAttend;

import java.util.List;

public interface ReAttendSereice {
    void startReAttendFlow(ReAttend reAttend);

    void approve(ReAttend reAttend);

    List<ReAttend> listTasks(String userName);

    List<ReAttend> listReAttend(String username);
}
