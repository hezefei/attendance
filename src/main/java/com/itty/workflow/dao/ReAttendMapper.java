package com.itty.workflow.dao;

import com.itty.workflow.entity.ReAttend;

import java.util.List;

public interface ReAttendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ReAttend record);

    int insertSelective(ReAttend record);

    ReAttend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ReAttend record);

    int updateByPrimaryKey(ReAttend record);

    List<ReAttend> selectReAttendRecord(String username);
}