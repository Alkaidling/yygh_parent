package com.alkaid.yygh.user.service;

import com.alkaid.yygh.model.user.UserInfo;
import com.alkaid.yygh.vo.user.LoginVo;
import com.alkaid.yygh.vo.user.UserAuthVo;
import com.alkaid.yygh.vo.user.UserInfoQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
    //以下为前台用户系统使用的操作

    //用户手机号登录接口
    Map<String, Object> loginUser(LoginVo loginVo);
    //根据openid判断数据库是否存在微信扫码人的信息
    UserInfo selectWxInfoOpenid(String openid);
    //用户认证
    void userAuth(Long userId, UserAuthVo userAuthVo);

    //以下为后台管理系统使用的操作

    //用户列表（条件查询带分页）
    IPage<UserInfo> selectPage(Page<UserInfo> pageParam, UserInfoQueryVo userInfoQueryVo);
    //用户锁定
    void lock(Long userId, Integer status);
    //用户详情
    Map<String, Object> show(Long userId);
    //认证审批（修改用户状态）
    void approval(Long userId, Integer authStatus);
}
