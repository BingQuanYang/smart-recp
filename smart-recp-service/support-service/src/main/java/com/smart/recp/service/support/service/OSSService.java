package com.smart.recp.service.support.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author ybq
 */
public interface OSSService {
    String upload(MultipartFile file) throws IOException;
}
