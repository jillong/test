package com.example.springai.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
public class FileMinioVectorMapping {

    private String id;

    /**
     * 上传的文件名称
     */
    private String fileName;

    /**
     * 该文件分割出的多段向量文本ID
     */
    private String vectorIds;

    /**
     * Minio文件url
     */
    private String url;


    /**
     * 创建时间/上传时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
}
