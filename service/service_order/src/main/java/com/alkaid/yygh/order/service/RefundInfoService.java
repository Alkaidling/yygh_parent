package com.alkaid.yygh.order.service;

import com.alkaid.yygh.model.order.PaymentInfo;
import com.alkaid.yygh.model.order.RefundInfo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-18 16:51
 * @ClassName RefundInfoService
 * @Description:
 */

public interface RefundInfoService extends IService<RefundInfo> {
    /**
     * 保存退款记录
     * @param paymentInfo
     */
    RefundInfo saveRefundInfo(PaymentInfo paymentInfo);

}
