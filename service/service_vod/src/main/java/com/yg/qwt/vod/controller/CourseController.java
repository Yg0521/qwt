package com.yg.qwt.vod.controller;

import com.yg.qwt.model.vod.Course;
import com.yg.qwt.model.vod.Teacher;
import com.yg.qwt.result.Result;
import com.yg.qwt.vo.vod.CourseFormVo;
import com.yg.qwt.vo.vod.CoursePublishVo;
import com.yg.qwt.vo.vod.CourseQueryVo;
import com.yg.qwt.vod.service.CourseService;
import com.yg.qwt.vod.utils.CosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
@Api(tags = "课程管理接口")
@RestController
@RequestMapping("/admin/vod/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "courseVo", value = "查询对象", required = false)
                    CourseQueryVo courseQueryVo) {
        Map<String,Object> map = courseService.findPage(page,limit, courseQueryVo);
        return Result.ok(map);
    }

    @ApiOperation(value = "添加课程")
    @PostMapping("/save")
    public Result save(@RequestBody CourseFormVo courseFormVo) {
        Long courseId = courseService.saveCourseInfo(courseFormVo);
        return Result.ok(courseId);
    }

    @ApiOperation(value = "根据Id获取课程信息")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        CourseFormVo course = courseService.getCourseFormVoById(id);
        return Result.ok(course);
    }

    @ApiOperation(value = "修改课程")
    @PutMapping("/update")
    public Result updateById(@RequestBody CourseFormVo courseFormVo) {
        courseService.updateCourseById(courseFormVo);
        return Result.ok(courseFormVo.getId());
    }

    @ApiOperation(value = "根据id获取课程发布信息")
    @GetMapping("/getCoursePublishVo/{id}")
    public Result getCoursePublishVoById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return Result.ok(coursePublishVo);
    }

    @ApiOperation(value = "根据id发布课程")
    @PutMapping("/publishCourseById/{id}")
    public Result publishCourseById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long id){

        boolean result = courseService.publishCourseById(id);
        return Result.ok(result);
    }

    @ApiOperation(value = "删除课程")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        // 先同步删除存储在云服务上的头像
        Course course = courseService.getById(id);
        String cover = course.getCover();
        String targetDate = new SimpleDateFormat("yyyy").format(course.getUpdateTime());
        if (!StringUtils.isEmpty(cover)){
            CosUtil.deleteFile(cover,targetDate);
        }
        // 再执行删除操作
        courseService.removeCourseById(id);
        return Result.ok();
    }
}

