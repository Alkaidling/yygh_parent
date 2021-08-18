package com.alkaid.yygh.order.service;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-18 15:27
 * @ClassName WeixinService
 * @Description:
 */

public interface WeixinService {
    //生成微信支付扫描的二维码
    Map createNative(Long orderId);
    //调用微信的接口实现状态查询
    Map<String, String> queryPayStatus(Long orderId);
    /***
     * 退款
     * @param orderId
     * @return
     */
    Boolean refund(Long orderId);

}
