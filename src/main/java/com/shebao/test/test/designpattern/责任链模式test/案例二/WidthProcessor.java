package com.shebao.test.test.designpattern.责任链模式test.案例二;

public class WidthProcessor implements Processor{
    @Override
    public boolean process(Product product, ProcessorChain chain) {
        if( 20 < product.getWidth() && product.getWidth() <50){
            System.out.println("产品宽度符合标准");
            return chain.process(product,chain);
        }
        System.out.println("产品宽度不符合标准");
        return false;
    }
}
