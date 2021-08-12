package com.alkaid.yygh.hosp.service;

import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.data.domain.Page;

import java.util.List;
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
    //医院列表 （分页条件查询mongodb）
    Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo);
    //更新医院上线状态
    void updateStatus(String id, Integer status);
    //医院详情信息
    Map<String, Object> getHospById(String id);
    //获取医院名称
    String getHospName(String hoscode);
    //根据医院名称查询
    List<Hospital> findByHosName(String hosname);
    //根据医院编号获取所有预约挂号信息
    Map<String, Object> item(String hoscode);
}
