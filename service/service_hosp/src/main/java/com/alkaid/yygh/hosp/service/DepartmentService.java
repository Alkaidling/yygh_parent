package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Department;
import com.alkaid.yygh.vo.hosp.DepartmentQueryVo;
import org.springframework.data.domain.Page;

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
}
