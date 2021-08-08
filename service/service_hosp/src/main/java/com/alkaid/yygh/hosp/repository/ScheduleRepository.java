package com.alkaid.yygh.hosp.repository;

import com.alkaid.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-08 16:22
 * @ClassName ScheduleRepository
 * @Description:
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule,String> {
    //根据hoscode和HosScheDuleId查询排班信息
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);
}
