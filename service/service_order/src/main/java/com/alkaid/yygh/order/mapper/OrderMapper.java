package com.alkaid.yygh.order.mapper;

import com.alkaid.yygh.model.order.OrderInfo;
import com.alkaid.yygh.vo.order.OrderCountQueryVo;
import com.alkaid.yygh.vo.order.OrderCountVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-16 17:11
 * @ClassName OrderMapper
 * @Description:
 */

public interface OrderMapper extends BaseMapper<OrderInfo> {
    //查询预约统计数据的方法
    List<OrderCountVo> selectOrderCount(@Param("vo") OrderCountQueryVo orderCountQueryVo);
}
