package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Department;
import com.alkaid.yygh.model.hosp.Schedule;
import com.alkaid.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-08 16:22
 * @ClassName ScheduleService
 * @Description:
 */

public interface ScheduleService {
    //上传排班接口
    void save(Map<String, Object> paramMap);
    //查询排班接口
    Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo);
    //删除排班接口
    void remove(String hoscode, String hosScheduleId);
    //根据医院编号 和 科室编号 查询排班规则
    Map<String, Object> getRuleSchedule(Long page, Long limit, String hoscode, String depcode);
    //根据医院编号 和 科室编号 和 工作日期 查询排班详细信息
    List<Schedule> getDetailSchedule(String hoscode, String depcode, String workDate);
}
