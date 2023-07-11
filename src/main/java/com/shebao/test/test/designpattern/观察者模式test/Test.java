package com.shebao.test.test.designpattern.观察者模式test;

public class Test {
    public static void main(String[] args) {
        // 观察者模式
        IDesign iDesign = new IDesignService();

        IObserver iObserver = new IObserverService();

        iDesign.add(iObserver);

        iDesign.implement();
    }
}
