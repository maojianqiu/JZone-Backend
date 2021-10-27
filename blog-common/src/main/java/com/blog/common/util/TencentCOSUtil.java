package com.blog.common.util;

import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Random;

/**
 * @Author: zh
 * @Date: 2020/6/5 16:22
 */

public class TencentCOSUtil {
    // 存储桶名称
    @Value("${tecentcos.bucketName}")
    private  String bucketName ;
    //secretId 秘钥id
    @Value("${tecentcos.SecretId}")
    private  String SecretId ;
    //SecretKey 秘钥
    @Value("${tecentcos.SecretKey}")
    private  String SecretKey ;
    // 腾讯云 自定义文件夹名称
    @Value("${tecentcos.prefix}")
    private  String prefix ;
    // 访问域名
    @Value("${tecentcos.URL}")
    public  String URL ;


    public  String uploadfile(MultipartFile file){
        // 创建COS 凭证
        COSCredentials credentials = new BasicCOSCredentials(SecretId,SecretKey);
        // 配置 COS 区域 就购买时选择的区域 我这里是 广州（guangzhou）
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));

        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials,clientConfig);
        String fileName = file.getOriginalFilename();
        try {
            // 获取输入流
            InputStream inputStream =  new BufferedInputStream(file.getInputStream());
            ObjectMetadata objectMetadata = new ObjectMetadata();

            // 设置输入流长度为500
            // 这里要强调一下，因为腾讯云支持本地文件上传和文件流上传，为了不必要的麻烦所以选择文件流上传，根据官方文档，为了避免oom，必须要设置元数据并告知输入流长度
            objectMetadata.setContentLength(file.getSize());

//            String substring = fileName.substring(fileName.lastIndexOf("."));
            Random random = new Random();
            fileName =prefix+random.nextInt(10000)+System.currentTimeMillis()+fileName;
            // 具体用法参考官方文档
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }finally {

            cosClient.shutdown();
        }
        return URL+fileName;
    }


    public Boolean delload(String fileurl){
        // 创建COS 凭证
        COSCredentials credentials = new BasicCOSCredentials(SecretId,SecretKey);
        // 配置 COS 区域 就购买时选择的区域 我这里是 广州（guangzhou）
        ClientConfig clientConfig = new ClientConfig(new Region("ap-beijing"));

        String[] keys = fileurl.split("/");
        String key = "/"+ keys[3] +"/"+ keys[4];

        // 创建 COS 客户端连接
        COSClient cosClient = new COSClient(credentials,clientConfig);
        try {
            // 具体用法参考官方文档
            cosClient.deleteObject(bucketName ,key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cosClient.shutdown();
        }
        return true;
    }

}

