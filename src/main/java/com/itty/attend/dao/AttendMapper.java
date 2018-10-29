package com.itty.attend.dao;

import com.itty.attend.entity.Attend;
import com.itty.attend.vo.QueryCondition;

import java.util.List;

public interface AttendMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Attend record);

    int insertSelective(Attend record);

    Attend selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Attend record);

    int updateByPrimaryKey(Attend record);

    Attend selectRecord(Long userId);

    int selectCount(QueryCondition condition);

    List<Attend> selectAttendPage(QueryCondition condition);

    List<Long> selectTodayAbsence();

    void batchInsert(List<Attend> attendList);

    List<Attend> selectTodayEveningAbsence();

}