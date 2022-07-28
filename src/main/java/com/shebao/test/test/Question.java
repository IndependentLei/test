package com.shebao.test.test;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    @ExcelProperty(value = "题目id",index = 0)
    private String id;            //题目ID
    @ExcelProperty(value = "企业id",index = 1)
    private String companyId;   //所属企业
    @ExcelProperty(value = "类目id",index = 2)
    private String catalogId;   //题目所属目录ID
}
