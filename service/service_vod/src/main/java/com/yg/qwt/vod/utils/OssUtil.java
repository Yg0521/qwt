package com.yg.qwt.vod.utils;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.internal.OSSHeaders;
import com.aliyun.oss.model.*;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * 文件上传工具
 * */
public class OssUtil {
    public static String uploadFile(MultipartFile file) {

        String endpoint = AConstantPropertiesUtil.END_POINT;
        String bucketName = AConstantPropertiesUtil.BUCKET_NAME;
        String secretId = AConstantPropertiesUtil.ACCESS_KEY_ID;
        String secretKey = AConstantPropertiesUtil.ACCESS_KEY_SECRET;
        // 生成 cos 客户端。
        OSS client = new OSSClientBuilder().build(endpoint, secretId, secretKey);


        try {
            // 指定要上传的文件
            InputStream inputStream = file.getInputStream();
            // 指定文件将要存放的存储桶
            // 指定文件上传到 OOS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = UUID.randomUUID().toString().replaceAll("-", "") +
                    file.getOriginalFilename();
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            key = dateUrl + "/" + key;

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream);
            // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
            metadata.setObjectAcl(CannedAccessControlList.Private);
            putObjectRequest.setMetadata(metadata);
            // 上传文件。
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);

            System.out.println(JSON.toJSONString(putObjectResult));
            //https://qwt-upload.oss-cn-guangzhou.aliyuncs.com/2023/07/19/7a831934da15418eb983e42421b0ff3b20110708211357.jpg
            String url = "https://" + bucketName + endpoint.substring(8) + "/" + key;
            return url;
        } catch (Exception clientException) {
            clientException.printStackTrace();
            return null;
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }
}
