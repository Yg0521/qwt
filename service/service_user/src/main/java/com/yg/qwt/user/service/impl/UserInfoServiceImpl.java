package com.yg.qwt.user.service.impl;


import com.yg.qwt.model.user.UserInfo;
import com.yg.qwt.user.mapper.UserInfoMapper;
import com.yg.qwt.user.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author yg
 * @since 2023-10-07
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {

}
