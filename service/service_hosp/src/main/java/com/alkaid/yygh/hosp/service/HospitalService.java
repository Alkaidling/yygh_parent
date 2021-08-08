package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-07 17:41
 * @ClassName HospitalService
 * @Description:
 */

public interface HospitalService {
    //上传医院接口
    void save(Map<String, Object> paramMap);
    //根据医院编号查询
    Hospital getByHoscode(String hoscode);
}
