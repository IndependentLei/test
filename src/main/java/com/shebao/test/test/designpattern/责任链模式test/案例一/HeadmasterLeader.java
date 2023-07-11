package com.shebao.test.test.designpattern.责任链模式test.案例一;

public class HeadmasterLeader extends AbstractLeader{
    @Override
    protected void handlerRequest(int days) {
        if(days < 10){
            System.out.println("HeadmasterLeader允许你请假");
        }else {
            if(null != getNext()){
                getNext().handlerRequest(days);
            }else {
                System.out.println("您的请假天数太多,没有人可以批准");
            }
        }
    }
}
