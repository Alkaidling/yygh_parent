package com.alkaid.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alkaid.yygh.hosp.repository.ScheduleRepository;
import com.alkaid.yygh.hosp.service.ScheduleService;
import com.alkaid.yygh.model.hosp.Department;
import com.alkaid.yygh.model.hosp.Schedule;
import com.alkaid.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-08 16:23
 * @ClassName SecheduleService
 * @Description:
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;

    //上传排班接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //paramMap转成schedule
        String jsonString = JSONObject.toJSONString(paramMap);
        Schedule schedule = JSONObject.parseObject(jsonString, Schedule.class);

        //根据hoscode和HosScheduleId查询排班信息
        Schedule scheduleExist = scheduleRepository.
                getScheduleByHoscodeAndHosScheduleId(schedule.getHoscode(),schedule.getHosScheduleId());

        //判断是否存在
        if(scheduleExist != null){
            scheduleExist.setUpdateTime(new Date());
            scheduleExist.setIsDeleted(0);
            scheduleExist.setStatus(1);
            scheduleRepository.save(scheduleExist);
        }else {
            schedule.setCreateTime(new Date());
            schedule.setUpdateTime(new Date());
            schedule.setIsDeleted(0);
            schedule.setStatus(1);
            scheduleRepository.save(schedule);
        }
    }

    //查询排班接口
    @Override
    public Page<Schedule> findPageSchedule(int page, int limit, ScheduleQueryVo scheduleQueryVo) {

        //创建pageable对象，设置当前页和每页记录数
        Pageable pageable = PageRequest.of(page - 1,limit);

        Schedule schedule = new Schedule();
        BeanUtils.copyProperties(scheduleQueryVo,schedule);
        schedule.setIsDeleted(0);
        schedule.setStatus(1);

        //创建example对象
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        Example<Schedule> example = Example.of(schedule,matcher);

        Page<Schedule> all = scheduleRepository.findAll(example, pageable);
        return all;
    }

    //删除排班接口
    @Override
    public void remove(String hoscode, String hosScheduleId) {
        //根据医院编号和排班编号查询信息
        Schedule schedule = scheduleRepository.getScheduleByHoscodeAndHosScheduleId(hoscode, hosScheduleId);
        if(schedule != null)
            scheduleRepository.deleteById(schedule.getId());
    }
}
