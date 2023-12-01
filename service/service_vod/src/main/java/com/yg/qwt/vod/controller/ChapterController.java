package com.yg.qwt.vod.controller;


import com.yg.qwt.model.vod.Chapter;
import com.yg.qwt.result.Result;
import com.yg.qwt.vo.vod.ChapterVo;
import com.yg.qwt.vod.service.ChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
@Api(tags = "课程章节接口")
@RestController
@RequestMapping("/admin/vod/chapter")
public class ChapterController {

    @Autowired
    private ChapterService chapterService;

    // 获取章节小结列表
    @ApiOperation("嵌套章节数据列表")
    @GetMapping("/getNestedTreeList/{courseId}")
    public Result getNestedTreeList(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable Long courseId){
        List<ChapterVo> chapterVoList = chapterService.getNestedTreeList(courseId);
        return Result.ok(chapterVoList);
    }

    // 添加章节
    @ApiOperation("添加章节")
    @PostMapping("/save")
    public Result save(@RequestBody Chapter chapter) {
        chapterService.save(chapter);
        return Result.ok(null);
    }

    // 修改-根据id查询
    @ApiOperation("修改-根据id查询")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable Long id) {
        Chapter chapter = chapterService.getById(id);
        return Result.ok(chapter);
    }

    // 修改-最终实现
    @ApiOperation("修改-最终实现")
    @PostMapping("/update")
    public Result update(@RequestBody Chapter chapter) {
        chapterService.updateById(chapter);
        return Result.ok(null);
    }

    // 删除章节
    @ApiOperation("删除章节")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable Long id) {
        chapterService.removeById(id);
        return Result.ok(null);
    }
}

