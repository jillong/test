package com.example.springai.service;

import com.example.springai.common.PagedResult;
import com.example.springai.entity.FileMinioVectorMapping;
import org.springframework.web.multipart.MultipartFile;

public interface KnowFileService {
    void knowFileStore(MultipartFile file) throws Exception;

    PagedResult<FileMinioVectorMapping> getKnowFile(String filter, Integer page, Integer pageSize);
}
