package com.shebao.test;

import com.google.common.base.Splitter;
import org.junit.jupiter.api.Test;

import java.util.List;


class FourTestApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public void test1(){
        String str = "1,2,3,4";
        List<String> strList = Splitter.on(",").splitToList(str);
        System.out.println(strList);
    }

}
