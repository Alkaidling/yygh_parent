package com.alkaid.yygh.cmn.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alkaid.yygh.cmn.listener.DictListener;
import com.alkaid.yygh.cmn.mapper.DictMapper;
import com.alkaid.yygh.cmn.service.DictService;
import com.alkaid.yygh.model.cmn.Dict;
import com.alkaid.yygh.vo.cmn.DictEeVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-06 15:34
 * @ClassName DictServiceImpl
 * @Description:
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictMapper, Dict> implements DictService {


    //根据数据id查询子数据列表(id = parentid)
    @Override
    @Cacheable(value = "dict",keyGenerator = "keyGenerator")
    public List<Dict> findChildData(Long id) {

        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        List<Dict> dictList = baseMapper.selectList(wrapper);
        //向list集合每个dict对象中设置hasChildren
        for (Dict dict : dictList) {
            Long dictId = dict.getId();
            boolean isChildren = this.isChildren(dictId);
            dict.setHasChildren(isChildren);
        }
        //dictList.forEach(dict -> dict.setHasChildren(isChildren(dict.getId())));
        return dictList;
    }

    //导出数据字典
    @Override
    public void exportDictData(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = "dict";
            response.setHeader("Content-disposition", "attachment;filename="+ fileName + ".xlsx");
            //查询数据库
            List<Dict> dictList = baseMapper.selectList(null);
            List<DictEeVo> dictVoList = new ArrayList<>(dictList.size());
            //Dict -> DictEeVo
            for(Dict dict : dictList) {
                DictEeVo dictVo = new DictEeVo();
                BeanUtils.copyProperties(dict, dictVo);
                dictVoList.add(dictVo);
            }

            EasyExcel.write(response.getOutputStream(), DictEeVo.class).sheet("dict").doWrite(dictVoList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //导入数据字典
    @CacheEvict(value = "dict", allEntries=true)
    @Override
    public void importDataDict(MultipartFile file) {
        try {
            EasyExcel.read(file.getInputStream(),DictEeVo.class,new DictListener(baseMapper)).sheet().doRead();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //根据dictcode和value值进行查询
    @Override
    public String getDictName(String dictcode, String value) {
        //如果dictcode值为空，直接根据value查询
        if(StringUtils.isEmpty(dictcode)){
            QueryWrapper<Dict> wrapper = new QueryWrapper<>();
            wrapper.eq("value",value);
            Dict dict = baseMapper.selectOne(wrapper);
            return dict.getName();
        }else {//如果dictcode值不为空，根据dictcode和value查询
            //根据dictcode查询对象，得到id值
            Dict codeDict = this.getDictByDictCode(dictcode);
            Long parentId = codeDict.getId();
            //根据parentid和value值查询
            Dict dict = baseMapper.selectOne(new QueryWrapper<Dict>()
                    .eq("parent_id", parentId)
                    .eq("value", value));
            return dict.getName();
        }
    }

    //根据dictcode获取下级节点
    @Override
    public List<Dict> findByDictCode(String dictcode) {
        //根据dictcode获取对应id
        Dict dict = this.getDictByDictCode(dictcode);
        //根据id获取子数据列表
        List<Dict> list = this.findChildData(dict.getId());

        return list;
    }

    //根据dictcode查询对象
    private Dict getDictByDictCode(String dictcode){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("dict_code",dictcode);
        Dict codeDict = baseMapper.selectOne(wrapper);
        return codeDict;
    }

    //判断id下是否由子数据
    private boolean isChildren(Long id){
        QueryWrapper<Dict> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        return count > 0;

    }

}
