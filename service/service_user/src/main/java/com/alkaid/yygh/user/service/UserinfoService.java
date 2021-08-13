package com.alkaid.yygh.user.service;

import com.alkaid.yygh.vo.user.LoginVo;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-13 14:21
 * @ClassName UserinfoService
 * @Description:
 */

public interface UserinfoService {
    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);
}
