package com.yg.qwt.vod.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yg.qwt.model.vod.Teacher;
import com.yg.qwt.result.Result;
import com.yg.qwt.vo.vod.TeacherQueryVo;
import com.yg.qwt.vod.service.TeacherService;
import com.yg.qwt.vod.utils.CosUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author yg
 * @since 2023-07-17
 */
@Api(tags = "讲师管理接口")
@RestController
@RequestMapping(value="/admin/vod/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查看所有讲师列表")
    @GetMapping("/findAll")
    public Result findAll(){

//        try {
//            int i = 1/0;
//        } catch (Exception e){
//            throw new QwtExceptionHandler(201,"执行了自定义异常");
//        }

        List<Teacher> list = teacherService.list();
        return Result.ok(list).message("查询数据成功");
    }

    @ApiOperation("逻辑删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result removeById(@ApiParam(name = "id", value = "ID", required = true)
                                  @PathVariable Long id){
        // 先同步删除存储在云服务上的头像
        Teacher teacher = teacherService.getById(id);
        String avatar = teacher.getAvatar();
        String targetDate = new SimpleDateFormat("yyyy").format(teacher.getUpdateTime());
        if (!StringUtils.isEmpty(avatar)){
            CosUtil.deleteFile(avatar,targetDate);
        }
        // 再执行删除操作
        boolean isSuccess = teacherService.removeById(id);
        if (isSuccess){
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("/findQueryPage/{current}/{limit}")
    public Result index(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "teacherVo", value = "查询对象", required = false)
            @RequestBody(required = false) TeacherQueryVo teacherQueryVo) {
        //创建page对象，传递当前页和每页记录数
        Page<Teacher> pageParam = new Page<>(current, limit);
        //判断TeacherQueryVo对象是否为空
        if (teacherQueryVo == null){
            IPage<Teacher> pageModel = teacherService.page(pageParam, null);
            return Result.ok(pageModel);
        }else {
            //获取条件值
            String name = teacherQueryVo.getName();//讲师名称
            Integer level = teacherQueryVo.getLevel();//讲师级别
            String joinDateBegin = teacherQueryVo.getJoinDateBegin();//开始时间
            String joinDateEnd = teacherQueryVo.getJoinDateEnd();//结束时间
            //封装条件
            QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(name)) {
                wrapper.like("name",name);
            }
            if(!StringUtils.isEmpty(level)) {
                wrapper.eq("level",level);
            }
            if(!StringUtils.isEmpty(joinDateBegin)) {
                wrapper.ge("join_date",joinDateBegin);
            }
            if(!StringUtils.isEmpty(joinDateEnd)) {
                wrapper.le("join_date",joinDateEnd);
            }
            //调用方法得到分页查询结果
            IPage<Teacher> pageModel = teacherService.page(pageParam, wrapper);
            return Result.ok(pageModel);
        }
    }

    @ApiOperation(value = "新增")
    @PostMapping("/save")
    public Result save(@RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据Id查询")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        Teacher teacher = teacherService.getById(id);
        return Result.ok(teacher);
    }

    @ApiOperation(value = "修改的最终实现")
    @PostMapping("/update")
    public Result updateById(@RequestBody Teacher teacher) {
        teacherService.updateById(teacher);
        return Result.ok();
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        // 先同步删除存储在云服务上的头像
        for (Long id : idList) {
            Teacher teacher = teacherService.getById(id);
            String avatar = teacher.getAvatar();
            String targetDate = new SimpleDateFormat("yyyy").format(teacher.getUpdateTime());
            if (!StringUtils.isEmpty(avatar)){
                CosUtil.deleteFile(avatar,targetDate);
            }
        }
        // 再执行删除操作
        teacherService.removeByIds(idList);
        return Result.ok();
    }
}

