package com.alkaid.yygh.order.service;

import com.alkaid.yygh.model.order.OrderInfo;
import com.alkaid.yygh.model.order.PaymentInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-18 15:32
 * @ClassName PaymentService
 * @Description:
 */

public interface PaymentService extends IService<PaymentInfo> {
    //向支付记录表添加信息
    void savePaymentInfo(OrderInfo order, Integer paymentType);
    //更改订单状态，处理支付结果
    void paySuccess(String out_trade_no, Integer status, Map<String, String> resultMap);
    /**
     * 获取支付记录
     * @param orderId
     * @param paymentType
     * @return
     */
    PaymentInfo getPaymentInfo(Long orderId, Integer paymentType);

}
