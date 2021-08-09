package com.alkaid.yygh.cmn.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-09 17:02
 * @ClassName DictFeignClient
 * @Description: 模块间服务调用接口，调用service-cmn
 */
@FeignClient("service-cmn")
@Repository
public interface DictFeignClient {
    //根据dictcode和value值进行查询
    @GetMapping("/admin/cmn/dict/getName/{dictcode}/{value}")
    public String getName(@PathVariable("dictcode") String dictcode, @PathVariable("value") String value);

    //根据value值进行查询
    @GetMapping("/admin/cmn/dict/getName/{value}")
    public String getName(@PathVariable("value") String value);
}
