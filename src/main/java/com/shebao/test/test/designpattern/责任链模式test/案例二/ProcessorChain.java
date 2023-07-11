package com.shebao.test.test.designpattern.责任链模式test.案例二;

import com.google.api.client.util.Lists;

import java.util.List;

public class ProcessorChain {
    private List<Processor> processorList = Lists.newArrayList();

    private int index;
    public ProcessorChain  addProcessor(Processor processor){
        processorList.add(processor);
        return this;
    }

    public boolean process(Product product,ProcessorChain chain){
        if(index == processorList.size()){
            return true;
        }
        Processor processor = processorList.get(index);
        if( null != processor ){
            index++;
            return processor.process(product,chain);
        }
        throw new RuntimeException("获取责任链异常");
    }
}
