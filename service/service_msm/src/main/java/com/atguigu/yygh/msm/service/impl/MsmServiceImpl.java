package com.atguigu.yygh.msm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.yygh.msm.service.MsmService;
import com.alkaid.yygh.vo.msm.MsmVo;
import com.netflix.client.ClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.rmi.ServerException;
import java.util.HashMap;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {
    @Override
    public boolean send(String phone, String code) {
        //判断手机号是否为空
        //if(StringUtils.isEmpty(phone)) {
        //    return false;
        //}
        //整合阿里云短信服务
        //设置相关参数
        //DefaultProfile profile = DefaultProfile.
        //        getProfile(ConstantPropertiesUtils.REGION_Id,
        //                ConstantPropertiesUtils.ACCESS_KEY_ID,
        //                ConstantPropertiesUtils.SECRECT);
        //IAcsClient client = new DefaultAcsClient(profile);
        //CommonRequest request = new CommonRequest();
        ////request.setProtocol(ProtocolType.HTTPS);
        //request.setMethod(MethodType.POST);
        //request.setDomain("dysmsapi.aliyuncs.com");
        //request.setVersion("2017-05-25");
        //request.setAction("SendSms");
        //
        ////手机号
        //request.putQueryParameter("PhoneNumbers", phone);
        ////签名名称
        //request.putQueryParameter("SignName", "我的谷粒在线教育网站");
        ////模板code
        //request.putQueryParameter("TemplateCode", "SMS_180051135");
        ////验证码  使用json格式   {"code":"123456"}
        //Map<String,Object> param = new HashMap();
        //param.put("code",code);
        //request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));
        //
        ////调用方法进行短信发送
        //try {
        //    CommonResponse response = client.getCommonResponse(request);
        //    System.out.println(response.getData());
        //    return response.getHttpResponse().isSuccess();
        //} catch (ServerException e) {
        //    e.printStackTrace();
        //} catch (ClientException e) {
        //    e.printStackTrace();
        //}
        //return false;
        return true;
    }

    //mq发送短信封装
    @Override
    public boolean send(MsmVo msmVo) {
        if(!StringUtils.isEmpty(msmVo.getPhone())) {
            String code = (String)msmVo.getParam().get("code");
            boolean send = this.send(msmVo.getPhone(), code);
            return send;
        }
        return true;
    }

}
