package com.shebao.test;

import com.google.common.base.Splitter;
import com.shebao.test.controller.file.BigFileDealWithController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class FourTestApplicationTests {

    @Resource
    BigFileDealWithController bigFileDealWithController;


    @Test
    public void test1(){
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
        System.out.println(bigFileDealWithController);
    }

}
