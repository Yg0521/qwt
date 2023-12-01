package com.yg.gwt;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateFolderSample {
    private static String endpoint = "https://oss-cn-guangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAI5tPdFNTZSmEbRd4KZ5qB";
    private static String accessKeySecret = "Hy3L3ZrpJ0dYNv5cStE2vi5sGtixGE";

    private static String bucketName = "qwt-upload";

    public static void main(String[] args) throws IOException {

        final String keySuffixWithSlash = "01.jpg";
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件流。
        String filePath= "E:\\IDEA项目\\开源项目\\qwt\\qwt_parent\\service\\service_vod\\001.jpg";
        /*
         * Constructs a client instance with your account for accessing OSS
         */
        OSS client = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {
            /*
             * Create an empty folder without request body, note that the key must be
             * suffixed with a slash
             */
            InputStream inputStream = new FileInputStream(filePath);
            client.putObject(bucketName, keySuffixWithSlash, inputStream);

        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorMessage());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } finally {
            if (client != null) {
                client.shutdown();
            }
        }
    }
}
