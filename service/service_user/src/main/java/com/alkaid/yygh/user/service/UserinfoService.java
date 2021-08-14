package com.alkaid.yygh.user.service;

import com.alkaid.yygh.model.user.UserInfo;
import com.alkaid.yygh.vo.user.LoginVo;
import com.alkaid.yygh.vo.user.UserAuthVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-13 14:21
 * @ClassName UserinfoService
 * @Description:
 */

public interface UserinfoService extends IService<UserInfo> {
    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);
    //根据openid判断数据库是否存在微信扫码人的信息
    UserInfo selectWxInfoOpenid(String openid);
    //用户认证
    void userAuth(Long userId, UserAuthVo userAuthVo);
}
