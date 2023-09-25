package com.shebao.test.controller.dataDesensitization;

import cn.hutool.core.util.RandomUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据脱敏测试
 */
@RestController
@RequestMapping("/dataDesensitization")
public class DataDesensitizationController {
    @GetMapping("/getData")
    public DataDesensitization dataDesensitization(){
        return DataDesensitization.builder()
                .name("张三")
                .bankCard(RandomUtil.randomString(18).toUpperCase())
                .idNo(RandomUtil.randomNumbers(18))
                .mobilePhone(RandomUtil.randomNumbers(11))
                .password(RandomUtil.randomNumbers(10))
                .build();
    }
}
