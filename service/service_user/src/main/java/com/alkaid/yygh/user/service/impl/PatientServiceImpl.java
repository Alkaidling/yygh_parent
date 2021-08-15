package com.alkaid.yygh.user.service.impl;

import com.alkaid.yygh.cmn.client.DictFeignClient;
import com.alkaid.yygh.enums.DictEnum;
import com.alkaid.yygh.model.user.Patient;
import com.alkaid.yygh.user.mapper.PatientMapper;
import com.alkaid.yygh.user.service.PatientService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-15 14:43
 * @ClassName PatientServiceImpl
 * @Description:
 */
@Service
public class PatientServiceImpl extends ServiceImpl<PatientMapper, Patient> implements PatientService {
    @Autowired
    private DictFeignClient dictFeignClient;

    //获取就诊人的列表
    @Override
    public List<Patient> findAllUserId(Long userId) {
        //根据userid查询所有就诊人的信息列表
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id",userId);
        List<Patient> patientList = baseMapper.selectList(queryWrapper);
        //通过远程调用得到编码对应的具体内容，查询数据字典表
        patientList.stream().forEach(item -> {
            this.packPatient(item);
        });

        return patientList;
    }

    //根据id获取就诊人的信息
    @Override
    public Patient getPatientId(Long id) {
        Patient patient = baseMapper.selectById(id);

        return this.packPatient(patient);
    }

    //patient对象的其他参数的封装
    private Patient packPatient(Patient patient) {
        //根据证件编码，获取证件类型的具体值
        String certificatesTypeString = dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(), patient.getCertificatesType());

        //联系人证件类型
        String contactsCertificatesTypeString =
                dictFeignClient.getName(DictEnum.CERTIFICATES_TYPE.getDictCode(),patient.getContactsCertificatesType());
        //省
        String provinceString = dictFeignClient.getName(patient.getProvinceCode());
        //市
        String cityString = dictFeignClient.getName(patient.getCityCode());
        //区
        String districtString = dictFeignClient.getName(patient.getDistrictCode());
        patient.getParam().put("certificatesTypeString", certificatesTypeString);
        patient.getParam().put("contactsCertificatesTypeString", contactsCertificatesTypeString);
        patient.getParam().put("provinceString", provinceString);
        patient.getParam().put("cityString", cityString);
        patient.getParam().put("districtString", districtString);
        patient.getParam().put("fullAddress", provinceString + cityString + districtString + patient.getAddress());

        return patient;
    }
}
