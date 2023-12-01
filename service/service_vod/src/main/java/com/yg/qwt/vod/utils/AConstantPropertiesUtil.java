package com.yg.qwt.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 常量类，读取配置文件application.properties中的配置
 * InitializingBean 这个类的作用是在初始化bean时读取application.properties文件
 */
@Component
public class AConstantPropertiesUtil implements InitializingBean {

    @Value("${aliyun.oos.file.endpoint}")
    private String endpoint;

    @Value("${aliyun.oos.file.secretid}")
    private String secretId;

    @Value("${aliyun.oos.file.secretkey}")
    private String secretKey;

    @Value("${aliyun.oos.file.bucketname}")
    private String bucketName;

    public static String END_POINT;
    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECRET;
    public static String BUCKET_NAME;

    @Override
    public void afterPropertiesSet() throws Exception {
        END_POINT = endpoint;
        ACCESS_KEY_ID = secretId;
        ACCESS_KEY_SECRET = secretKey;
        BUCKET_NAME = bucketName;
    }
}
