package com.example.springai.utils;

import io.minio.*;
import io.minio.http.Method;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class MinioUtil {

    private static final String format = "yyyy-MM-dd HH:mm:ss";

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.access-key}")
    private String accessKey;

    @Value("${minio.secret-key}")
    private String secretKey;

    @Value("${minio.bucket-name}")
    private String bucketName;
    private MinioClient minioClient;

    /**
     * 初始化minio客户端
     */
    @PostConstruct
    public void init() {
        try {
            log.info("Minio Initialize........................");
            this.minioClient = MinioClient.builder().endpoint(minioUrl).credentials(accessKey, secretKey).build();
            createBucket(bucketName);
            log.info("Minio Initialize........................successful");
        } catch (Exception e) {
            log.error("初始化minio配置异常: 【{}】", e.getMessage());
        }
    }

    public void createBucket(String bucketName) throws Exception {
        if (NotExistBucket(bucketName)) {
            this.minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        }
    }

    public boolean NotExistBucket(String bucketName) throws Exception {
        return !this.minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
    }

    public String upload(MultipartFile file) throws Exception {

        if (NotExistBucket(bucketName)) {
            createBucket(bucketName);
        }

        final String filename = file.getOriginalFilename();
        String objectFileName = filename + "--" + new SimpleDateFormat(format).format(new Date());
        this.minioClient.putObject(PutObjectArgs
                .builder()
                .bucket(bucketName)
                .object(objectFileName)
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .build());
        return getPreviewFileUrl(objectFileName);
    }

    public void delete(String objectFileName) throws Exception {
        this.minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectFileName).build());
    }

    public String getPreviewFileUrl(String fileName) throws Exception {
        return this.minioClient.getPresignedObjectUrl(
                GetPresignedObjectUrlArgs.builder()
                        .method(Method.GET)
                        .bucket(bucketName)
                        .object(fileName)
                        .expiry(24, TimeUnit.HOURS)
                        .build());
    }

    public static String getMinioFileName(String url) {
        int endIndex = url.contains("?") ? url.indexOf("?") : url.length();
        String substring = url.substring(0, endIndex);
        return URLDecoder.decode(
                substring.substring(substring.lastIndexOf("/") + 1), StandardCharsets.UTF_8);
    }
}
