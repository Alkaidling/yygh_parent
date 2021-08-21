package com.alkaid.yygh.order.service;

import com.alkaid.yygh.model.order.OrderInfo;
import com.alkaid.yygh.vo.order.OrderCountQueryVo;
import com.alkaid.yygh.vo.order.OrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-16 17:10
 * @ClassName OrderService
 * @Description:
 */

public interface OrderService extends IService<OrderInfo> {
    //生成挂号订单
    Long saveOrder(String scheduleId, Long patientId);
    //根据订单id查询订单的详情
    OrderInfo getOrder(String orderId);
    //订单列表（条件查询带分页）
    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderQueryVo orderQueryVo);
    //获取订单
    Object show(Long orderId);
    //取消预约
    Boolean cancelOrder(Long orderId);
    //就诊提醒
    void patientTips();
    //预约统计方法
    Map<String,Object> getCountMap(OrderCountQueryVo orderCountQueryVo);
}
