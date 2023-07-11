package com.shebao.test.test.designpattern.观察者模式test;

import java.util.ArrayList;
import java.util.List;

public class IDesignService implements IDesign {

    private static final List<IObserver> iObserveList = new ArrayList<>();
    @Override
    public void add(IObserver iObserver) {
        iObserveList.add(iObserver);
    }

    @Override
    public void remove(IObserver iObserver) {
        iObserveList.remove(iObserver);
    }

    @Override
    public void implement() {
        for (IObserver iObserver : iObserveList) {
            iObserver.update();
        }
    }
}
