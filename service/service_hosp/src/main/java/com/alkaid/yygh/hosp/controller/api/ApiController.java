package com.alkaid.yygh.hosp.controller.api;

import com.alkaid.yygh.common.exception.YyghException;
import com.alkaid.yygh.common.helper.HttpRequestHelper;
import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.common.result.ResultCodeEnum;
import com.alkaid.yygh.common.utils.MD5;
import com.alkaid.yygh.hosp.service.DepartmentService;
import com.alkaid.yygh.hosp.service.HospitalService;
import com.alkaid.yygh.hosp.service.HospitalSetService;
import com.alkaid.yygh.hosp.service.ScheduleService;
import com.alkaid.yygh.model.hosp.Department;
import com.alkaid.yygh.model.hosp.Hospital;
import com.alkaid.yygh.model.hosp.Schedule;
import com.alkaid.yygh.vo.hosp.DepartmentQueryVo;
import com.alkaid.yygh.vo.hosp.ScheduleOrderVo;
import com.alkaid.yygh.vo.hosp.ScheduleQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-07 17:43
 * @ClassName ApiController
 * @Description: 访问医院信息的Api接口
 */
@RestController
@RequestMapping("/api/hosp")
public class ApiController {

    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    //删除排班接口
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //得到医院编号和排班编号
        String hoscode = (String) paramMap.get("hoscode");
        String hosScheduleId = (String) paramMap.get("hosScheduleId");

        scheduleService.remove(hoscode,hosScheduleId);
        return Result.ok();
    }

    //查询排班接口
    @PostMapping("schedule/list")
    public Result findSchedule(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //得到医院编号和科室编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        //当前页，每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page"))? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit"))? 1 : Integer.parseInt((String)paramMap.get("limit"));

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);

        //调用service方法
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page,limit,scheduleQueryVo);

        return Result.ok(pageModel);
    }

    //上传排班接口
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //调用service方法
        scheduleService.save(paramMap);

        return Result.ok();
    }

    //删除科室接口
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //得到医院编号
        String hoscode = (String) paramMap.get("hoscode");
        String depcode = (String) paramMap.get("depcode");

        //调用service方法
        departmentService.remove(hoscode,depcode);

        return Result.ok();
    }

    //查询科室接口
    @PostMapping("department/list")
    public Result findDepartment(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //得到医院编号
        String hoscode = (String) paramMap.get("hoscode");
        //当前页，每页记录数
        int page = StringUtils.isEmpty(paramMap.get("page"))? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmpty(paramMap.get("limit"))? 1 : Integer.parseInt((String)paramMap.get("limit"));

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);

        //调用service方法
        Page<Department> pageModel = departmentService.findPageDepartment(page,limit,departmentQueryVo);

        return Result.ok(pageModel);
    }

    //上传科室接口
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //md5加密对比
        //1.获取医院系统中传递过来的签名,进行md5加密的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递的医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查的签名也用md5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用DepartmentService方法
        departmentService.save(paramMap);

        return Result.ok();
    }

    //查询医院接口
    @PostMapping("hospital/show")
    public Result getHospital(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);
        //获取医院编号
        String hoscode = (String) paramMap.get("hoscode");

        //md5加密对比
        //1.获取医院系统中传递过来的签名,进行md5加密的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递的医院编号，查询数据库，查询签名
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查的签名也用md5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //调用service方法实现根据医院编号查询
        Hospital hospital = hospitalService.getByHoscode(hoscode);

        return Result.ok(hospital);
    }


    //上传医院接口
    @PostMapping("saveHospital")
    public Result saveHosp(HttpServletRequest request){
        //获取传递过来医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //md5加密对比
        //1.获取医院系统中传递过来的签名,进行md5加密的签名
        String hospSign = (String) paramMap.get("sign");
        //2.根据传递的医院编号，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);
        //3.把数据库查的签名也用md5加密
        String signKeyMD5 = MD5.encrypt(signKey);
        //4.判断签名是否一致
        if(!hospSign.equals(signKeyMD5)){
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }


        //传输过程中“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String) paramMap.get("logoData");
        logoData = logoData.replaceAll(" ","+");
        paramMap.put("logoData",logoData);

        //调用service中的方法保存到数据库
        hospitalService.save(paramMap);
        return Result.ok();
    }

}
