package com.shebao.test.controller.file;

import com.shebao.test.model.po.FilePo;
import com.shebao.test.model.po.MergePo;
import com.shebao.test.service.BigFileDealWithService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/bigFile")
public class BigFileDealWithController {

    @Resource
    BigFileDealWithService bigFileDealWithService;

    @PostMapping("/dealWith")
    public void dealWith(@RequestBody @Validated FilePo filePo){
        bigFileDealWithService.dealWith(filePo);
    }

    @PostMapping("/merge")
    public void merge(@RequestBody @Validated MergePo mergePo){
        bigFileDealWithService.merge(mergePo);
    }

}
