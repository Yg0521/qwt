package com.yg.qwt.activity.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yg.qwt.model.activity.CouponInfo;
import com.yg.qwt.model.activity.CouponUse;
import com.yg.qwt.vo.activity.CouponUseQueryVo;

/**
 * <p>
 * 优惠券信息 服务类
 * </p>
 *
 * @author yg
 * @since 2023-10-07
 */
public interface CouponInfoService extends IService<CouponInfo> {

    // 获取已使用优惠券列表
    IPage<CouponUse> selectCouponUsePage(Page<CouponUse> pageParam, CouponUseQueryVo couponUseQueryVo);
}
