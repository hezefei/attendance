package com.itty.attend.service;

import com.itty.attend.dao.AttendMapper;
import com.itty.attend.entity.Attend;
import com.itty.attend.vo.QueryCondition;
import com.itty.common.page.PageQueryBean;
import com.itty.common.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: hezefei
 * @Date: 2018/9/7 14:11
 * @Description:
 */
@Service("attendServiceImpl")
public class AttendServiceImpl implements AttendService {
    /**
     * 判断上下午
     **/

    private static final int NOON_HOUR = 12;
    private static final int NOON_MINUTE = 00;
    /**
     * 早晚上班时间判定
     */
    private static final int MORNING_HOUR = 9;
    private static final int MORNING_MINUTE = 30;
    private static final int EVENING_HOUR = 18;
    private static final int EVENING_MINUTE = 30;
    /**
     * 考勤异常状态
     */
    private static final Byte ATTEND_STATUS_ABNORMAL = 2;
    private static final Byte ATTEND_STATUS_NORMAL = 1;
    /**
     * 缺勤一整天
     */
    private static final Integer ABSENCE_DAY = 480;


    private Log log = LogFactory.getLog(AttendServiceImpl.class);
    @Autowired
    public AttendMapper attendMapper;

    @Override
    public void signAttend(Attend attend) throws Exception {
        try {
            Date date = new Date();
            attend.setAttendDate(date);
            attend.setAttendWeek((byte) DateUtils.getTodayWeek());
            //查询当天有没有打卡记录
            Attend attrnds = attendMapper.selectRecord(attend.getUserId());
            Date noon = DateUtils.getDate(NOON_HOUR, NOON_MINUTE);
            Date morningAttend = DateUtils.getDate(MORNING_HOUR, MORNING_MINUTE);

            if (attrnds == null) {
                if (date.compareTo(noon) <= 0) {
                    //打卡时间 早于12点 上午打卡
                    attend.setAttendMorning(date);

                    if (date.compareTo(morningAttend) > 0) {
                        //大于九点半迟到了

                        attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        attend.setAbsence(DateUtils.getMunite(morningAttend, date));
                    }
                } else {
                    attend.setAttendEvening(date);
                }
                attendMapper.insertSelective(attend);
            } else {
                if (date.compareTo(noon) <= 0) {
                    //打卡时间 早于12点 上午打卡
                    return;
                } else {
                    //晚上打卡
                    attrnds.setAttendEvening(date);
                    //判断打卡时间是不是18.30以后是不是早退
                    Date eveningAttend = DateUtils.getDate(EVENING_HOUR, EVENING_MINUTE);
                    if (date.compareTo(eveningAttend) < 0) {
                        //早于下午六点半 早退
                        attrnds.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                        attrnds.setAbsence(DateUtils.getMunite(date, eveningAttend));
                    } else {
                        attrnds.setAttendStatus(ATTEND_STATUS_NORMAL);
                        attrnds.setAbsence(0);
                    }
                    attendMapper.updateByPrimaryKeySelective(attrnds);
                }
            }

        } catch (Exception e) {
            log.error("用户签到异常", e);
            throw e;

        }
    }

    @Override
    public PageQueryBean listAttend(QueryCondition condition) {
        //根据条件查询总条数
        int count = attendMapper.selectCount(condition);
        PageQueryBean pageResult = new PageQueryBean();
        if (count > 0) {
            pageResult.setTotalRows(count);
            pageResult.setCurrentPage(condition.getCurrentPage());
            pageResult.setPageSize(condition.getPageSize());
            List<Attend> attendList = attendMapper.selectAttendPage(condition);
            pageResult.setItems(attendList);
        }
        return pageResult;
    }

    /**
     * @Description: 检查考勤异常数据
     * @author: wangjianbin
     * @Param: []
     * @Return: void
     * @Date: 15:30 2017/6/26
     */
    @Override
    @Transactional
    public void checkAttend() {

        //查询缺勤用户ID 插入打卡记录  并且设置为异常 缺勤480分钟
        List<Long> userIdList = attendMapper.selectTodayAbsence();
        if (CollectionUtils.isNotEmpty(userIdList)) {
            List<Attend> attendList = new ArrayList<>();
            for (Long userId : userIdList) {
                Attend attend = new Attend();
                attend.setUserId(userId);
                attend.setAttendDate(new Date());
                attend.setAttendWeek((byte) DateUtils.getTodayWeek());
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendList.add(attend);
            }
            attendMapper.batchInsert(attendList);
        }
        // 检查晚打卡 将下班未打卡记录设置为异常
        List<Attend> absenceList = attendMapper.selectTodayEveningAbsence();
        if (CollectionUtils.isNotEmpty(absenceList)) {
            for (Attend attend : absenceList) {
                attend.setAbsence(ABSENCE_DAY);
                attend.setAttendStatus(ATTEND_STATUS_ABNORMAL);
                attendMapper.updateByPrimaryKeySelective(attend);
            }
        }

    }
}
