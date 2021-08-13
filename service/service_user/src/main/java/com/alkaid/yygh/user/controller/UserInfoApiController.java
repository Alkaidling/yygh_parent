package com.alkaid.yygh.user.controller;

import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.user.service.UserinfoService;
import com.alkaid.yygh.vo.user.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-13 14:20
 * @ClassName UserInfoApiController
 * @Description:
 */
@RestController
@RequestMapping("/api/user")
public class UserInfoApiController {
    @Autowired
    private UserinfoService userinfoService;

    //用户手机号登录接口
    @PostMapping("login")
    public Result login(@RequestBody LoginVo loginVo){
        Map<String,Object> info = userinfoService.loginUser(loginVo);
        return Result.ok(info);
    }


}
