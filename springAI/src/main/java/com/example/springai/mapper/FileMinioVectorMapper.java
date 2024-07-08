package com.example.springai.mapper;

import com.example.springai.entity.FileMinioVectorMapping;

import java.util.List;

public interface FileMinioVectorMapper {

    long countFiles(String filter);

    void createFileMinioVectorMapping(FileMinioVectorMapping fileMinioVectorMapping);

    List<FileMinioVectorMapping> getKnowFile(String filter, Integer offset, Integer pageSize);
}
