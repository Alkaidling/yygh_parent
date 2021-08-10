package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Department;
import com.alkaid.yygh.vo.hosp.DepartmentQueryVo;
import com.alkaid.yygh.vo.hosp.DepartmentVo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-08 15:30
 * @ClassName DepartmentService
 * @Description:
 */

public interface DepartmentService {
    //上传科室接口
    void save(Map<String, Object> paramMap);
    //分页查询科室接口
    Page<Department> findPageDepartment(int page, int limit, DepartmentQueryVo departmentQueryVo);
    //删除科室接口
    void remove(String hoscode, String depcode);
    //根据医院编号查询医院所有科室列表
    List<DepartmentVo> finDeptTree(String hoscode);
    //根据医院编号，科室编号 查询科室名称
    String getDepName(String hoscode, String depcode);
}
