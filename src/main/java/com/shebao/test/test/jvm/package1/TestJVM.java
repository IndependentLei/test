package com.shebao.test.test.jvm.package1;

public class TestJVM {
    public static void main(String[] args) {
        //返回虚拟机试图使用的最大内存
        long max = Runtime.getRuntime().maxMemory();
        //返回虚拟机的初始化总内存
        long total = Runtime.getRuntime().totalMemory();

        //默认情况下：分配的总内存是电脑运行时内存的1/4，而初始化内存是1/64
        System.out.println("max"+max+"字节\t"+(max/(double)1024/1024)+"MB");
        System.out.println("total"+total+"字节\t"+(total/(double)1024/1024)+"MB");

        //添加：VM options
        //-Xms1024m -Xmx1024m -XX:+PrintGCDetails
    }
}
