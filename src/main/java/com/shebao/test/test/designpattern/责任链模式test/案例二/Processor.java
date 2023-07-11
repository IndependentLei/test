package com.shebao.test.test.designpattern.责任链模式test.案例二;

public interface Processor {
    boolean process(Product product,ProcessorChain chain);
}
