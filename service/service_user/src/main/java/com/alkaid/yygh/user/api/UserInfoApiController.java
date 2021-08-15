package com.alkaid.yygh.user.api;

import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.model.user.UserInfo;
import com.alkaid.yygh.user.service.UserinfoService;
import com.alkaid.yygh.user.utils.AuthContextHolder;
import com.alkaid.yygh.vo.user.LoginVo;
import com.alkaid.yygh.vo.user.UserAuthVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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

    //用户认证接口
    @PostMapping("auth/userAuth")
    public Result userAuth(@RequestBody UserAuthVo userAuthVo, HttpServletRequest request){
        //传递两个参数，第一个参数为用户id，第二个参数为认证数据的vo对象
        userinfoService.userAuth(AuthContextHolder.getUserId(request),userAuthVo);
        return Result.ok();
    }

    //获取用户id信息
    @GetMapping("auth/getUserInfo")
    public Result getUserInfo(HttpServletRequest request){
        Long userId = AuthContextHolder.getUserId(request);
        UserInfo userInfo = userinfoService.getById(userId);
        return Result.ok(userInfo);
    }


}
