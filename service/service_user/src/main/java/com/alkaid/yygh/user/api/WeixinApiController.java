package com.alkaid.yygh.user.api;

import com.alibaba.fastjson.JSONObject;
import com.alkaid.yygh.common.helper.JwtHelper;
import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.model.user.UserInfo;
import com.alkaid.yygh.user.service.UserinfoService;
import com.alkaid.yygh.user.utils.ConstantWxPropertiesUtil;
import com.alkaid.yygh.user.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-14 14:39
 * @ClassName WeixinApiController
 * @Description: 微信操作接口
 */
@Controller
@RequestMapping("/api/ucenter/wx")
public class WeixinApiController {

    @Autowired
    private UserinfoService userinfoService;

    //微信扫码后进行回调的方法
    @GetMapping("callback")
    public String callback(String code,String state){
        //1、获取临时票据code
        //2、用code和微信id、密钥，请求微信固定地址，得到两个值
        //使用code和appid以及appscrect换取access_token
        StringBuffer baseAccessTokenUrl = new StringBuffer()
                .append("https://api.weixin.qq.com/sns/oauth2/access_token")
                .append("?appid=%s")
                .append("&secret=%s")
                .append("&code=%s")
                .append("&grant_type=authorization_code");
        String accessTokenUrl = String.format(baseAccessTokenUrl.toString(),
                ConstantWxPropertiesUtil.WX_OPEN_APP_ID,
                ConstantWxPropertiesUtil.WX_OPEN_APP_SECRET,
                code);
        try {
            //使用httpclient请求地址
            String accesstokenInfo = HttpClientUtils.get(accessTokenUrl);
            //从返回的字符串里获取两个值 openid access_token
            JSONObject jsonObject = JSONObject.parseObject(accesstokenInfo);
            String access_token = jsonObject.getString("access_token");
            String openid = jsonObject.getString("openid");

            //根据openid判断数据库是否存在微信扫码人的信息
            UserInfo userInfo = userinfoService.selectWxInfoOpenid(openid);
            if (userInfo == null) { //数据库不存在用户信息
                //3、用openid和access_token再请求微信的地址得到扫码人的信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, access_token, openid);
                String resultInfo = HttpClientUtils.get(userInfoUrl);
                System.out.println(resultInfo);
                JSONObject resultUserInfoJson = jsonObject.parseObject(resultInfo);
                String nickname = resultUserInfoJson.getString("nickname");       //昵称
                String headimgurl = resultUserInfoJson.getString("headimgurl");   //头像

                //将扫码人的信息添加到数据库
                userInfo = new UserInfo();
                userInfo.setNickName(nickname);
                userInfo.setOpenid(openid);
                userInfo.setStatus(1);
                userinfoService.save(userInfo);
            }

            //返回name和token字符串
            HashMap<String, String> map = new HashMap<>();
            String name = userInfo.getName();
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getNickName();
            }
            if(StringUtils.isEmpty(name)) {
                name = userInfo.getPhone();
            }
            map.put("name", name);
            //前端判断openid是否为空，不为空就绑定手机号，为空不需要绑定手机号
            if(StringUtils.isEmpty(userInfo.getPhone())) {
                //判断userinfo是否有手机号，如果为空，返回openid
                map.put("openid", userInfo.getOpenid());
            } else {
                //手机号不为空，返回openid为空
                map.put("openid", "");
            }
            //使用jwt生成token字符串
            String token = JwtHelper.createToken(userInfo.getId(), name);
            map.put("token", token);

            //跳转到前端页面
            return "redirect:" + ConstantWxPropertiesUtil.YYGH_BASE_URL + "/weixin/callback?token="
                    +map.get("token")+"&openid="+map.get("openid")+"&name="+URLEncoder.encode(map.get("name"),"utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    //1、生成微信二维码
    //返回生成二维码需要的参数
    @GetMapping("getLoginParam")
    @ResponseBody
    public Result genQrConnect(){
        String redirectUri = null;
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("appid", ConstantWxPropertiesUtil.WX_OPEN_APP_ID);
            String wxOpenRedirectUrl = ConstantWxPropertiesUtil.WX_OPEN_REDIRECT_URL;
            redirectUri = URLEncoder.encode(wxOpenRedirectUrl, "utf-8");
            map.put("redirectUri", redirectUri);
            map.put("scope", "snsapi_login");
            map.put("state", System.currentTimeMillis()+"");//System.currentTimeMillis()+""
            return Result.ok(map);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }


    }


    //2、回调的方法，得到扫码人的信息
}
