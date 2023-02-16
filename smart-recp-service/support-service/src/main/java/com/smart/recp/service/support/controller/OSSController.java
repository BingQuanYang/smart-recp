package com.smart.recp.service.support.controller;


import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.support.service.OSSService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * @author ybq
 */
@Api(tags = "阿里云OSS对象云存储")
@RestController
@RequestMapping("/support/oss")
public class OSSController {

    @Resource
    OSSService ossService;

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传图片")
    @ApiImplicitParam(name = "file", value = "文件")
    public RestResult<String> upload(@RequestBody MultipartFile file) throws IOException {
        return RestResult.success(ossService.upload(file));
    }
}
