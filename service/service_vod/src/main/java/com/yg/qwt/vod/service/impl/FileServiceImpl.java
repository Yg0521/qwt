package com.yg.qwt.vod.service.impl;

import com.yg.qwt.vod.service.FileService;
import com.yg.qwt.vod.utils.CosUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileServiceImpl implements FileService {

    //文件上传(这里可选阿里云和腾讯云两种）
    @Override
    public String upload(MultipartFile file) {
        return CosUtil.uploadFile(file);
    }
}
