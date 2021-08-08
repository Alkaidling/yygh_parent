package com.alkaid.yygh.hosp.service.impl;

import com.alkaid.yygh.hosp.mapper.HospitalSetMapper;
import com.alkaid.yygh.hosp.service.HospitalSetService;
import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 16:29
 * @ClassName HospitalSetServiceImpl
 * @Description:
 */
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet> implements HospitalSetService {
    //根据传递的医院编号，查询数据库，查询签名
    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode",hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);

        return hospitalSet.getSignKey();
    }

}
