package com.shebao.test.test.designpattern.观察者模式test;

public class IObserverService implements IObserver{
    @Override
    public void update() {
        System.out.println("IObserverService update !!!");
    }
}
