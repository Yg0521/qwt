package com.yg.qwt.vod.service;

import com.yg.qwt.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 视频来访者记录表 服务类
 * </p>
 *
 * @author yg
 * @since 2023-08-02
 */
public interface VideoVisitorService extends IService<VideoVisitor> {

    // 查看课程统计数据
    Map<String, Object> findCount(Long courseId, String startDate, String endDate);
}
