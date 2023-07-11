package com.shebao.test.test.designpattern.责任链模式test.案例一;

import lombok.Data;

@Data
public abstract class AbstractLeader {
    protected int days;
    protected AbstractLeader next;
    protected abstract void handlerRequest(int days);
}
