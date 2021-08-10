package com.alkaid.yygh.hosp.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alkaid.yygh.cmn.client.DictFeignClient;
import com.alkaid.yygh.hosp.repository.HospitalRepository;
import com.alkaid.yygh.hosp.service.HospitalService;
import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.vo.hosp.HospitalQueryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-07 17:41
 * @ClassName HospitalServiceImpl
 * @Description:
 */
@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    private HospitalRepository hospitalRepository;

    //注入远程调用client接口
    @Autowired
    private DictFeignClient dictFeignClient;

    //上传医院接口
    @Override
    public void save(Map<String, Object> paramMap) {
        //把map集合装换成hospital对象
        String mapString = JSONObject.toJSONString(paramMap);
        Hospital hospital = JSONObject.parseObject(mapString, Hospital.class);
        //判断是否存在数据
        String hoscode = hospital.getHoscode();
        Hospital hospitalExist = hospitalRepository.getHospitalByHoscode(hoscode);
        //存在，进行修改
        if(hospitalExist != null){
            hospital.setStatus(hospitalExist.getStatus());
            hospital.setCreateTime(hospitalExist.getCreateTime());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }else {
            //不存在，进行添加
            hospital.setStatus(0);
            hospital.setCreateTime(new Date());
            hospital.setUpdateTime(new Date());
            hospital.setIsDeleted(0);
            hospitalRepository.save(hospital);
        }

    }

    //根据医院编号查询
    @Override
    public Hospital getByHoscode(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);

        return hospital;
    }

    //医院列表 （分页条件查询mongodb）
    @Override
    public Page<Hospital> selectHospPage(Integer page, Integer limit, HospitalQueryVo hospitalQueryVo) {

        //创建pageable对象
        Pageable pageable = PageRequest.of(page-1,limit);
        //构建条件匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                .withIgnoreCase(true);
        //hospitalQueryVo转成hospital
        Hospital hospital = new Hospital();
        BeanUtils.copyProperties(hospitalQueryVo,hospital);
        //创建example实例
        Example<Hospital> example = Example.of(hospital, matcher);
        //调用方法实现查询
        Page<Hospital> pages = hospitalRepository.findAll(example, pageable);

        //获取list集合，遍历进行医院等级封装
        pages.getContent().stream().forEach(item -> {
            this.setHopitalHosType(item);
        });

        return pages;
    }

    //更新医院上线状态
    @Override
    public void updateStatus(String id, Integer status) {
        //根据id获取对象
        Hospital hospital = hospitalRepository.findById(id).get();
        //设置值
        hospital.setStatus(status);
        hospital.setUpdateTime(new Date());
        hospitalRepository.save(hospital);
    }

    //医院详情信息
    @Override
    public Map<String, Object> getHospById(String id) {
        Hospital hospital = this.setHopitalHosType(hospitalRepository.findById(id).get());
        Map<String, Object> result = new HashMap<>();
        //医院基本信息包含医院等级
        result.put("hospital",hospital);
        //单独处理更直观
        result.put("bookingRule", hospital.getBookingRule());
        //不需要重复返回
        hospital.setBookingRule(null);

        return result;
    }
    //获取医院名称
    @Override
    public String getHospName(String hoscode) {
        Hospital hospital = hospitalRepository.getHospitalByHoscode(hoscode);
        if(hospital != null)
            return hospital.getHosname();
        return null;
    }

    //进行医院等级封装
    private Hospital setHopitalHosType(Hospital hospital) {
        //根据dictcode和value值获取医院的等级名称
        String hostypeString = dictFeignClient.getName("Hostype", hospital.getHostype());
        //查询省市地区
        String provinceString = dictFeignClient.getName(hospital.getProvinceCode());
        String cityString = dictFeignClient.getName(hospital.getCityCode());
        String districtString = dictFeignClient.getName(hospital.getDistrictCode());

        hospital.getParam().put("fullAddress",provinceString+cityString+districtString);
        hospital.getParam().put("hostypeString",hostypeString);

        return hospital;
    }
}
