package com.alkaid.yygh.user.service.impl;

import com.alkaid.yygh.common.exception.YyghException;
import com.alkaid.yygh.common.helper.JwtHelper;
import com.alkaid.yygh.common.result.ResultCodeEnum;
import com.alkaid.yygh.enums.AuthStatusEnum;
import com.alkaid.yygh.model.user.UserInfo;
import com.alkaid.yygh.user.mapper.UserInfoMapper;
import com.alkaid.yygh.user.service.UserinfoService;
import com.alkaid.yygh.vo.user.LoginVo;
import com.alkaid.yygh.vo.user.UserAuthVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-13 14:21
 * @ClassName UserInfoServiceImpl
 * @Description:
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserinfoService {


    //用户手机号登录接口
    @Override
    public Map<String, Object> loginUser(LoginVo loginVo) {

        //1、从loginVo里获取输入的手机号和验证码
        String phone = loginVo.getPhone();
        String code = loginVo.getCode();

        //2、判断是否为空
        if(StringUtils.isEmpty(phone) || StringUtils.isEmpty(code))
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);

        //TODO 3、判断手机验证码和输入验证码是否一致
        if (!code.equals("123456")) {
            throw new YyghException(ResultCodeEnum.CODE_ERROR);
        }

        //绑定手机号码
        UserInfo userInfo = null;
        //userInfo!=null 说明微信扫码之后绑定手机再登录
        if(!StringUtils.isEmpty(loginVo.getOpenid())) {
            userInfo = this.selectWxInfoOpenid(loginVo.getOpenid());
            if(null != userInfo) {
                userInfo.setPhone(loginVo.getPhone());
                this.updateById(userInfo);
            } else {
                throw new YyghException(ResultCodeEnum.DATA_ERROR);
            }
        }

        //userInfo=null 说明手机直接登录
        if(null == userInfo) {
            //4、判断是否是第一次登录，根据手机号查询数据库，不存在相同手机号则为第一次登录
            QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
            wrapper.eq("phone",phone);
            userInfo = baseMapper.selectOne(wrapper);

            //5、第一次登录，添加信息到数据库
            if (userInfo == null) {
                userInfo = new UserInfo();
                userInfo.setName("");
                userInfo.setPhone(phone);
                userInfo.setStatus(1);
                baseMapper.insert(userInfo);
            }
        }

        //校验是否被禁用
        if(userInfo.getStatus() == 0) {
            throw new YyghException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        //5、不是第一次登录，直接登录
        //6、返回登录之后的信息(用户名，token)
        Map<String, Object> map = new HashMap<>();
        String name = userInfo.getName();
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getNickName();
        }
        if(StringUtils.isEmpty(name)) {
            name = userInfo.getPhone();
        }
        map.put("name", name);

        //token生成（jwt）
        String token = JwtHelper.createToken(userInfo.getId(), name);
        map.put("token", token);

        return map;
    }

    //根据openid判断数据库是否存在微信扫码人的信息
    @Override
    public UserInfo selectWxInfoOpenid(String openid) {
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("openid",openid);
        UserInfo userInfo = baseMapper.selectOne(queryWrapper);

        return userInfo;
    }

    //用户认证
    @Override
    public void userAuth(Long userId, UserAuthVo userAuthVo) {
        //根据用户id查询用户信息
        UserInfo userInfo = baseMapper.selectById(userId);
        //设置认证信息
        userInfo.setName(userAuthVo.getName());
        userInfo.setCertificatesType(userAuthVo.getCertificatesType());
        userInfo.setCertificatesNo(userAuthVo.getCertificatesNo());
        userInfo.setCertificatesUrl(userAuthVo.getCertificatesUrl());
        userInfo.setAuthStatus(AuthStatusEnum.AUTH_RUN.getStatus());
        //进行信息更新
        baseMapper.updateById(userInfo);
    }
}
