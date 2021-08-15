package com.alkaid.yygh.user.service;

import com.alkaid.yygh.model.user.Patient;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-15 14:42
 * @ClassName PatientService
 * @Description:
 */

public interface PatientService extends IService<Patient> {
    //获取就诊人的列表
    List<Patient> findAllUserId(Long userId);
    //根据id获取就诊人的信息
    Patient getPatientId(Long id);
}
