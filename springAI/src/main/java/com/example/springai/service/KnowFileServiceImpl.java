package com.example.springai.service;

import com.example.springai.common.PagedResult;
import com.example.springai.entity.FileMinioVectorMapping;
import com.example.springai.mapper.FileMinioVectorMapper;
import com.example.springai.utils.IncrementalIdGenerator;
import com.example.springai.utils.JacksonUtil;
import com.example.springai.utils.MinioUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class KnowFileServiceImpl implements KnowFileService {

    private final MinioUtil minioUtil;

    private final VectorStore vectorStore;

    private final IncrementalIdGenerator idGenerator;

    private final FileMinioVectorMapper fileMinioVectorMapper;


    public KnowFileServiceImpl(
            MinioUtil minioUtil, VectorStore vectorStore,
            IncrementalIdGenerator idGenerator, FileMinioVectorMapper fileMinioVectorMapper) {
        this.minioUtil = minioUtil;
        this.vectorStore = vectorStore;
        this.idGenerator = idGenerator;
        this.fileMinioVectorMapper = fileMinioVectorMapper;
    }

    @Override
    public void knowFileStore(MultipartFile file) throws Exception {

        if (file.isEmpty()) {
            return;
        }

        Resource resource = file.getResource();
        TikaDocumentReader tikaDocumentReader = new TikaDocumentReader(resource);
        List<Document> documents = tikaDocumentReader.get();
        // 文本切分为段落
        TextSplitter splitter = new TokenTextSplitter();
        List<Document> applyDocument = splitter.apply(documents);
        //存储到向量数据库
        this.vectorStore.add(applyDocument);
        //存储到minion
        String url = this.minioUtil.upload(file);
        long currMillis = System.currentTimeMillis();
        //关联向量文本和minion数据关系到数据库
        this.fileMinioVectorMapper.createFileMinioVectorMapping(
                FileMinioVectorMapping
                        .builder()
                        .id(idGenerator.nextId())
                        .fileName(file.getOriginalFilename())
                        .vectorIds(JacksonUtil.listToJson(applyDocument.stream().map(Document::getId).toList()))
                        .url(url)
                        .createTime(new Date(currMillis))
                        .updateTime(new Date(currMillis))
                        .build());
    }

    @Override
    public PagedResult<FileMinioVectorMapping> getKnowFile(String filter, Integer page, Integer pageSize) {
        long total = this.fileMinioVectorMapper.countFiles(filter);
        List<FileMinioVectorMapping> knowFiles
                = this.fileMinioVectorMapper.getPagedKnowFiles(filter, (page - 1) * pageSize, pageSize);

        PagedResult<FileMinioVectorMapping> pagedKnowFiles = new PagedResult<>();
        pagedKnowFiles.setData(knowFiles);
        pagedKnowFiles.setPage(page);
        pagedKnowFiles.setPageSize(pageSize);
        pagedKnowFiles.setTotal(total);
        return pagedKnowFiles;
    }

    @Override
    @Transactional
    public void deleteKnowFile(String id) throws Exception {
        FileMinioVectorMapping knowFileMapping = this.fileMinioVectorMapper.getKnowFile(id);
        String url = knowFileMapping.getUrl();
        String minioFileName = MinioUtil.getMinioFileName(url);
        this.minioUtil.delete(minioFileName);

        String vectorIds = knowFileMapping.getVectorIds();
        // 解析 JSON 字符串为数组
        List<String> vectorIdList = JacksonUtil.StringToJson(vectorIds, new TypeReference<List<String>>() {
        });
        if (CollectionUtils.isNotEmpty(vectorIdList)) {
            this.vectorStore.delete(vectorIdList);
        }

        this.fileMinioVectorMapper.deleteKnowFile(id);

    }
}
