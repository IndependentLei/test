package com.shebao.test.thread.four;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FourTest {
    private static List<String> list = new ArrayList<>();
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            list.add("数据"+i);
        }

        new Thread(()->{
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()){
                System.out.println(iterator.next());
            }
        }).start();

        new Thread(()->{
            for (int i = 0; i < 100; i++) {
                if(i % 5 == 0){
                    list.set(i,"修改的数据"+i);
                }
            }
        }).start();
    }
}
