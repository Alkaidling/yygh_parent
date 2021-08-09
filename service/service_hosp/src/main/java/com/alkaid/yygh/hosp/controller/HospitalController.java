package com.alkaid.yygh.hosp.controller;

import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.hosp.service.HospitalService;
import com.alkaid.yygh.hosp.service.HospitalSetService;
import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-09 16:27
 * @ClassName HospitalController
 * @Description: 医院管理
 */
@RestController
@RequestMapping("/admin/hosp/hospital")
@CrossOrigin
public class HospitalController {

    @Autowired
    HospitalService hospitalService;

    //医院列表 （分页条件查询mongodb）
    @GetMapping("list/{page}/{limit}")
    public Result listHospital(@PathVariable Integer page, @PathVariable Integer limit,
                               HospitalQueryVo hospitalQueryVo){
        Page<Hospital> pageModel = hospitalService.selectHospPage(page,limit,hospitalQueryVo);

        return Result.ok(pageModel);
    }

}
