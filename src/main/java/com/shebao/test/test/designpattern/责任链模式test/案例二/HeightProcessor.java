package com.shebao.test.test.designpattern.责任链模式test.案例二;

public class HeightProcessor implements Processor{
    @Override
    public boolean process(Product product, ProcessorChain chain) {
        if(20 < product.getHeight() &&  product.getHeight()< 50){
            System.out.println("产品高度符合规格");
            return chain.process(product,chain);
        }
        System.out.println("产品高度不符合标准");
        return false;
    }
}
