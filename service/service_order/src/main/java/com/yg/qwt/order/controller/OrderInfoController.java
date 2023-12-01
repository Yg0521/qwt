package com.yg.qwt.order.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yg.qwt.model.order.OrderInfo;
import com.yg.qwt.order.service.OrderInfoService;
import com.yg.qwt.result.Result;
import com.yg.qwt.vo.order.OrderInfoQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * <p>
 * 订单表 订单表 前端控制器
 * </p>
 *
 * @author yg
 * @since 2023-10-07
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping("/admin/order/orderInfo")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result getOrderList(@ApiParam(name = "page", value = "当前页码", required = true)
                                   @PathVariable Long page,
                               @ApiParam(name = "limit", value = "每页记录数", required = true)
                                   @PathVariable Long limit,
                               @ApiParam(name = "orderInfoVo", value = "查询对象", required = false)
                                           OrderInfoQueryVo orderInfoQueryVo){
        Page<OrderInfo> pageParam = new Page<>(page,limit);
        Map<String,Object> map = orderInfoService.findPageOrderInfo(pageParam, orderInfoQueryVo);
        return Result.ok(map);
    }

}

