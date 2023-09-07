package com.shebao.test.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FillPerson {
    private String name;
    private String age;
    private String sex;
    private String beizhu;

    public static void main(String[] args) {
        Map<String,String> extMap = JSON.parseObject("",new TypeReference<Map<String,String>>(){});
        System.out.println(extMap);
    }
}
