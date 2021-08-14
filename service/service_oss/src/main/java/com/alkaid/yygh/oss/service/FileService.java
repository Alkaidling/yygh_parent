package com.alkaid.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-14 22:02
 * @ClassName FileService
 * @Description:
 */

public interface FileService {
    //获取上传文件
    String upload(MultipartFile file);
}
