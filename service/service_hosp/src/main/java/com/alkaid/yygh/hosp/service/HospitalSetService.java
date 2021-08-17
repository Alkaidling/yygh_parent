package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.model.hosp.HospitalSet;
import com.alkaid.yygh.vo.order.SignInfoVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 16:28
 * @ClassName HospitalSetService
 * @Description:
 */

public interface HospitalSetService extends IService<HospitalSet> {
    //根据传递的医院编号，查询数据库，查询签名
    String getSignKey(String hoscode);
    //获取医院签名信息
    SignInfoVo getSignInfoVo(String hoscode);
}
