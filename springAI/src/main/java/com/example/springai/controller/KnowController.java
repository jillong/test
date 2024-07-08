package com.example.springai.controller;

import com.example.springai.common.HttpPagedResponse;
import com.example.springai.common.PagedResult;
import com.example.springai.entity.FileMinioVectorMapping;
import com.example.springai.service.KnowFileService;
import com.example.springai.utils.HttpResultUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Log4j2
@RestController
@RequestMapping("/know")
public class KnowController {

    private final KnowFileService knowFileService;

    public KnowController(KnowFileService knowFileService) {
        this.knowFileService = knowFileService;
    }

    @PostMapping
    public void addKnow(@RequestParam("files") List<MultipartFile> files) {
        try {
            if (CollectionUtils.isEmpty(files)) {
                return;
            }

            for (MultipartFile file : files) {
                this.knowFileService.knowFileStore(file);
            }
        } catch (Exception ex) {
            log.error("上传文件异常[{}]", ex.getMessage());
        }
    }

    @GetMapping
    public HttpPagedResponse<FileMinioVectorMapping> getKnowFile(
            @RequestParam(required = false) String filter,
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        try {
            PagedResult<FileMinioVectorMapping> knowFile = this.knowFileService.getKnowFile(filter, page, pageSize);

            return HttpResultUtils.createHttpPagedResult("200",knowFile, "获取文件信息成功", HttpStatus.OK);
        } catch (Exception ex) {
            log.error("获取文件信息失败[{}]", ex.getMessage());
            return HttpResultUtils.createHttpPagedResult("获取文件信息失败", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
