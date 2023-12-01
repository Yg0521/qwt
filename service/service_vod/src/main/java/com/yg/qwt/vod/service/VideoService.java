package com.yg.qwt.vod.service;

import com.yg.qwt.model.vod.Video;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
public interface VideoService extends IService<Video> {

    // 根据课程id删除小节
    void removeVideoByCourseId(Long id);

    // 根据小节id删除小节删除视频
    void removeVideoById(Long id);
}
