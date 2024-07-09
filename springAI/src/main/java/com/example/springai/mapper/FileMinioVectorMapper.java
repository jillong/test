package com.example.springai.mapper;

import com.example.springai.entity.FileMinioVectorMapping;

import java.util.List;

public interface FileMinioVectorMapper {

    long countFiles(String filter);

    void createFileMinioVectorMapping(FileMinioVectorMapping fileMinioVectorMapping);

    List<FileMinioVectorMapping> getPagedKnowFiles(String filter, Integer offset, Integer pageSize);

    FileMinioVectorMapping getKnowFile(String id);

    void deleteKnowFile(String id);
}
