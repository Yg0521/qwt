package com.yg.qwt.vod.mapper;

import com.yg.qwt.model.vod.VideoVisitor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yg.qwt.vo.vod.VideoVisitorCountVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 视频来访者记录表 Mapper 接口
 * </p>
 *
 * @author yg
 * @since 2023-08-02
 */
public interface VideoVisitorMapper extends BaseMapper<VideoVisitor> {

    // 显示统计数据
    List<VideoVisitorCountVo> findCount(@Param("courseId") Long courseId,
                                        @Param("startDate") String startDate,
                                        @Param("endDate")  String endDate);
}
