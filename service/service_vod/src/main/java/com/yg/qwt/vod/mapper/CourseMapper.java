package com.yg.qwt.vod.mapper;

import com.yg.qwt.model.vod.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yg.qwt.vo.vod.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
public interface CourseMapper extends BaseMapper<Course> {

    //根据id获取课程发布信息
    CoursePublishVo selectCoursePublishVoById(Long id);

}
