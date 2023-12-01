package com.yg.qwt.vod.service;

import com.yg.qwt.model.vod.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yg.qwt.vo.vod.CourseFormVo;
import com.yg.qwt.vo.vod.CoursePublishVo;
import com.yg.qwt.vo.vod.CourseQueryVo;

import java.util.Map;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
public interface CourseService extends IService<Course> {

    // 获取课程列表
    Map<String, Object> findPage(Long page, Long limit, CourseQueryVo courseQueryVo);

    // 添加课程
    Long saveCourseInfo(CourseFormVo courseFormVo);

    // 根据id获取课程信息
    CourseFormVo getCourseFormVoById(Long id);

    // 根据id修改课程信息
    void updateCourseById(CourseFormVo courseFormVo);

    // 根据id获取课程发布信息
    CoursePublishVo getCoursePublishVo(Long id);

    // 根据id发布课程
    boolean publishCourseById(Long id);

    // 课程删除
    void removeCourseById(Long id);
}
