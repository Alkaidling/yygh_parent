package com.alkaid.yygh.hosp.controller;

import com.alibaba.excel.util.StringUtils;
import com.alkaid.yygh.common.exception.YyghException;
import com.alkaid.yygh.common.utils.MD5;
import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.hosp.service.HospitalSetService;
import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.model.hosp.HospitalSet;
import com.alkaid.yygh.vo.hosp.HospitalSetQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 16:34
 * @ClassName HospitalSetController
 * @Description: 医院信息设置
 */
@Api(tags = "医院设置管理")  //swagger显示类的中文信息
@RestController
@RequestMapping("/admin/hosp/hospitalSet")
@CrossOrigin  //允许跨域访问
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    //1 查询设置表的所有信息
    @ApiOperation(value = "获取所有医院设置")
    @GetMapping("findAll")
    public Result findAllHospital(){
        //调用service中的方法
        List<HospitalSet> list = hospitalSetService.list();

        return Result.ok(list);
    }

    //2 删除医院设置信息
    @ApiOperation(value = "逻辑删除所有医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id){
        boolean flag = hospitalSetService.removeById(id);

        return flag ? Result.ok() : Result.fail();
    }

    //3 条件查询 带分页
    @ApiOperation(value = "条件查询 带分页")
    @PostMapping("findPage/{current}/{limit}")
    public Result findPageHospSet(@PathVariable long current, @PathVariable long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo){//RequestBody通过json传数据，false指可以为空
        //创建page对象，传递当前页，每页记录数
        Page<HospitalSet> page = new Page<>(current, limit);
        //构造条件
        QueryWrapper<HospitalSet> queryWrapper = new QueryWrapper<>();

        String hosname = hospitalSetQueryVo.getHosname();//医院名称
        String hoscode = hospitalSetQueryVo.getHoscode();//医院编号
        if(!StringUtils.isEmpty(hosname))
            queryWrapper.like("hosname",hospitalSetQueryVo.getHosname());
        if (!StringUtils.isEmpty(hoscode))
            queryWrapper.eq("hoscode",hospitalSetQueryVo.getHoscode());

        //调用方法实现查询
        Page<HospitalSet> pageHospitalSet = hospitalSetService.page(page, queryWrapper);

        return Result.ok(pageHospitalSet);
    }

    //4 添加医院设置
    @ApiOperation(value = "添加医院设置")
    @PostMapping("saveHospitalSet")
    public Result saveHospitalSet(@RequestBody HospitalSet hospitalSet){
        //设置状态 1 使用 ，0 不能用
        hospitalSet.setStatus(1);
        //签名密钥
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        //调用service方法
        boolean save = hospitalSetService.save(hospitalSet);

        return save?Result.ok():Result.fail();
    }

    //5 根据id获取医院设置
    @ApiOperation(value = "根据id获取医院设置")
    @GetMapping("getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    //6 修改医院设置
    @ApiOperation(value = "修改医院设置")
    @PostMapping("updateHospitalSet")
    public Result updateHospitalSet(@RequestBody HospitalSet hospitalSet){
        boolean flag = hospitalSetService.updateById(hospitalSet);
        return flag ? Result.ok() : Result.fail();
    }

    //7 批量删除医院设置
    @ApiOperation(value = "批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHospitalSet(@RequestBody List<Long> idList){
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }


    //8 医院设置锁定和解锁
    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable int status){
        //根据id查询医院设置信息
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        //设置医院状态
        hospitalSet.setStatus(status);
        //调用方法修改
        hospitalSetService.updateById(hospitalSet);

        return Result.ok();
    }


    //9 发送签名密钥
    @ApiOperation(value = "发送签名密钥")
    @PutMapping("sendKey/{id}")
    public Result lockHospitalSet(@PathVariable Long id){
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //TODO 发送短信
        return Result.ok();
    }
}
