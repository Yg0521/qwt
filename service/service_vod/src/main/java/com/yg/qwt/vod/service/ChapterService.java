package com.yg.qwt.vod.service;

import com.yg.qwt.model.vod.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yg.qwt.vo.vod.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
public interface ChapterService extends IService<Chapter> {

    // 章节小结列表
    List<ChapterVo> getNestedTreeList(Long courseId);

    // 根据课程id删除章节
    void removeChapterByCourseId(Long id);
}
