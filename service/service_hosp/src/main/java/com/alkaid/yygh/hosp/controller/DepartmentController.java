package com.alkaid.yygh.hosp.controller;

import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.hosp.service.DepartmentService;
import com.alkaid.yygh.vo.hosp.DepartmentVo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-10 16:28
 * @ClassName DepartmentController
 * @Description:
 */
@RestController
@RequestMapping("/admin/hosp/department")
@CrossOrigin
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    //根据医院编号查询医院所有科室列表
    @ApiOperation(value = "根据医院编号查询医院所有科室列表")
    @GetMapping("getDeptList/{hoscode}")
    public Result getDeptList(@PathVariable String hoscode){
        List<DepartmentVo> list = departmentService.finDeptTree(hoscode);
        return Result.ok(list);
    }
}
