package com.alkaid.yygh.hosp.repository;

import com.alkaid.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-08 15:29
 * @ClassName DepartmentRepository
 * @Description:
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    //根据hoscode和depcode查询科室信息
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
