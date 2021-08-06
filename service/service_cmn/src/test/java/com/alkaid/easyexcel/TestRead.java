package com.alkaid.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alkaid.easyexcel.ExcelListener;

public class TestRead {

    public static void main(String[] args) {
        // 读取文件路径
        String fileName = "D:\\桌面\\Java\\ex\\01.xlsx";
        //调用方法实现读取操作
        EasyExcel.read(fileName, UserData.class,new ExcelListener()).sheet().doRead();
    }
}
