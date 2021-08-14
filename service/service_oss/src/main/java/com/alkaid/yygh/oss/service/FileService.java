package com.alkaid.yygh.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-14 17:17
 * @ClassName FileService
 * @Description:
 */

public interface FileService {
    //获取上传文件
    String upLoad(MultipartFile file);
}
