package com.alkaid.yygh.order.api;

import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.enums.PaymentTypeEnum;
import com.alkaid.yygh.order.service.PaymentService;
import com.alkaid.yygh.order.service.WeixinService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-18 15:26
 * @ClassName WeixinController
 * @Description:
 */
@RestController
@RequestMapping("/api/order/weixin")
public class WeixinController {
    @Autowired
    private WeixinService weixinService;
    @Autowired
    private PaymentService paymentService;


    //生成微信支付扫描的二维码
    @GetMapping("/createNative/{orderId}")
    public Result createNative(@PathVariable Long orderId){
        Map map = weixinService.createNative(orderId);
        return Result.ok(map);
    }

    //查询支付状态
    @ApiOperation(value = "查询支付状态")
    @GetMapping("/queryPayStatus/{orderId}")
    public Result queryPayStatus(@PathVariable("orderId") Long orderId) {
        //调用微信的接口实现状态查询
        Map<String,String> resultMap = weixinService.queryPayStatus(orderId);

        //支付判断
        if (resultMap == null) {//出错
            return Result.fail().message("支付出错");
        }
        if ("SUCCESS".equals(resultMap.get("trade_state"))) {//如果成功
            //更改订单状态，处理支付结果
            String out_trade_no = resultMap.get("out_trade_no"); //订单编码
            paymentService.paySuccess(out_trade_no, PaymentTypeEnum.WEIXIN.getStatus(), resultMap);
            return Result.ok().message("支付成功");
        }
        return Result.ok().message("支付中");

    }

}
