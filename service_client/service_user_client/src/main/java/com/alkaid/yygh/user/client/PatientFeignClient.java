package com.alkaid.yygh.user.client;

import com.alkaid.yygh.model.user.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-16 17:28
 * @ClassName PatientFeignClient
 * @Description:
 */

@FeignClient(value = "service-user")
@Repository
public interface PatientFeignClient {
    //根据id获取就诊人信息
    @GetMapping("/api/user/patient/inner/get/{id}")
    Patient getPatient(@PathVariable("id") Long id);
}
