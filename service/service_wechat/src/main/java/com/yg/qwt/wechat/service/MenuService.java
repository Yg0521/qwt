package com.yg.qwt.wechat.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yg.qwt.model.wechat.Menu;
import com.yg.qwt.vo.wechat.MenuVo;

import java.util.List;

/**
 * <p>
 * 订单明细 订单明细 服务类
 * </p>
 *
 * @author yg
 * @since 2023-10-08
 */
public interface MenuService extends IService<Menu> {

    // 获取全部菜单
    List<MenuVo> findMenuInfo();

    // 获取一级菜单
    List<Menu> findMenuOneInfo();

    void syncMenu();

    void removeMenu();
}
