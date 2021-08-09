package com.alkaid.yygh.cmn.service;

import com.alkaid.yygh.model.cmn.Dict;
import com.alkaid.yygh.model.hosp.HospitalSet;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-06 15:34
 * @ClassName HospitalSetService
 * @Description:
 */

public interface DictService extends IService<Dict> {

    //根据数据id查询子数据列表
    List<Dict> findChildData(Long id);
    //导出数据字典
    void exportDictData(HttpServletResponse response);
    //导入数据字典
    void importDataDict(MultipartFile file);
    //根据dictcode和value值进行查询
    String getDictName(String dictcode, String value);
    //根据dictcode获取下级节点
    List<Dict> findByDictCode(String dictcode);
}
