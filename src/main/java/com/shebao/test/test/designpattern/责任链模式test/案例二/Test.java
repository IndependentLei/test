package com.shebao.test.test.designpattern.责任链模式test.案例二;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson2.JSON;
import com.google.api.client.util.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class Test {
    public static void main(String[] args) {


        List<Product> productList = Lists.newArrayList();
        for (int i = 0; i <50 ; i++) {
            Product product = new Product(RandomUtil.randomLong(1,50),RandomUtil.randomLong(1,50));
            productList.add(product);
        }

        log.info("productList {}", JSON.toJSONString(productList));
        for (Product product : productList) {
            ProcessorChain chain = new ProcessorChain();
            chain.addProcessor(new HeightProcessor())
                    .addProcessor(new WidthProcessor());

            boolean check = chain.process(product,chain);
            if(check){
                System.out.println("产品最终校验成功");
            }else {
                System.out.println("产品最终最终校验不成功");
            }
            System.out.println();
        }


    }
}
