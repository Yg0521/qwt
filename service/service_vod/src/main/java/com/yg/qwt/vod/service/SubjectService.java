package com.yg.qwt.vod.service;

import com.yg.qwt.model.vod.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author yg
 * @since 2023-07-31
 */
public interface SubjectService extends IService<Subject> {

    //查询下一层课程分类
    List<Subject> findChildSubject(Long id);

    /**
     * 导出
     * @param response
     */
    void exportData(HttpServletResponse response);

    void importDictData(MultipartFile file);
}
