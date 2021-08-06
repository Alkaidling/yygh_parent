package com.alkaid.yygh.cmn.controller;

import com.alkaid.yygh.cmn.service.DictService;
import com.alkaid.yygh.common.result.Result;
import com.alkaid.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-06 15:36
 * @ClassName DictController
 * @Description:
 */
@Api(tags = "数据字典接口")
@RestController
@RequestMapping(value = "/admin/cmn/dict")
@CrossOrigin  //允许跨域访问
public class DictController {

    @Autowired
    private DictService dictService;

    //导入数据字典
    @ApiOperation(value = "导入数据字典")
    @PostMapping("importData")
    public Result importData(MultipartFile file){
        dictService.importDataDict(file);
        return Result.ok();
    }

    //导出数据字典
    @ApiOperation(value = "导出数据字典")
    @GetMapping("exportData")
    public void exportDict(HttpServletResponse response) {
        dictService.exportDictData(response);
    }

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id){
        List<Dict> list = dictService.findChildData(id);
        return Result.ok(list);
    }


}
