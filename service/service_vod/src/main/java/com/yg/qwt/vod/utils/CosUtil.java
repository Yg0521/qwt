package com.yg.qwt.vod.utils;

import com.alibaba.fastjson.JSON;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpProtocol;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.joda.time.DateTime;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

public class CosUtil {
    private static final String endpoint = TConstantPropertiesUtil.END_POINT;
    private static final String bucketName = TConstantPropertiesUtil.BUCKET_NAME;
    private static final String secretId = TConstantPropertiesUtil.ACCESS_KEY_ID;
    private static final String secretKey = TConstantPropertiesUtil.ACCESS_KEY_SECRET;

    //初始化 cos 客户端
    public static COSClient getCOSClient(){
        // 1 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 2 设置 bucket 的地域
        ClientConfig clientConfig = new ClientConfig(new Region(endpoint));
        clientConfig.setHttpProtocol(HttpProtocol.https);
        // 3 生成 cos 客户端。
        return new COSClient(cred, clientConfig);
    }

    // 文件上传
    public static String uploadFile(MultipartFile file) {

        // 获取 cos 客户端
        COSClient client = getCOSClient();

        try {
            // 指定要上传的文件
            InputStream inputStream = file.getInputStream();
            // 指定文件将要存放的存储桶
            // 指定文件上传到 COS 上的路径，即对象键。例如对象键为folder/picture.jpg，则表示将文件 picture.jpg 上传到 folder 路径下
            String key = UUID.randomUUID().toString().replaceAll("-", "") +
                    file.getOriginalFilename();
            String dateUrl = new DateTime().toString("yyyy/MM/dd");
            key = dateUrl + "/" + key;

            // 创建PutObjectRequest对象。
            ObjectMetadata objectMetadata = new ObjectMetadata();
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, inputStream,objectMetadata);
            // 上传文件。
            PutObjectResult putObjectResult = client.putObject(putObjectRequest);

            System.out.println(JSON.toJSONString(putObjectResult));
            //https://qwt-upload-1319747905.cos.ap-guangzhou.myqcloud.com/2023/07/28/e5b8f3ccc2e244ca9bbd084a787ee20c20110708211357.jpg
            String url = "https://"+bucketName+"."+"cos"+"."+endpoint+".myqcloud.com"+"/"+key;
            return url;
        } catch (Exception clientException) {
            clientException.printStackTrace();
            return null;
        } finally {
            client.shutdown();
        }
    }

    // 删除文件
    public static void deleteFile(String url,String targetDate){
        // 获取 cos 客户端
        COSClient client = getCOSClient();

        // 删除文件
        try {
            // 查找目标子字符串的索引位置
            int startIndex = url.indexOf(targetDate);
            if (startIndex != -1) {
                // 获取文件路径，从目标子字符串的索引位置开始截取到字符串末尾
                String key = url.substring(startIndex);
                System.out.println("key: " + key); // Output: "2023/07/28/f2e43e3057ab413d91411dd83f646a2420110708211357.jpg"
                // 执行文件删除
                client.deleteObject(bucketName, key);
                System.out.println("文件删除成功！");
            } else {
                System.out.println("目标子字符串不存在");
            }
        } catch (Exception e) {
            System.out.println("文件删除失败：" + e.getMessage());
        } finally {
            // 关闭 COS 客户端
            client.shutdown();
        }
    }


}
