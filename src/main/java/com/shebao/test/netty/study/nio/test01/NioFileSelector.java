package com.shebao.test.netty.study.nio.test01;

public class NioFileSelector {
    public static void main(String[] args) {
        /**
         * 1.当客户端连接时，会通过ServerSocketChannel得到SocketChannel
         * 2.将socketChannel注册到Selector_上register(Selector sel,int ops),selector_上可以注册多个SocketChannel
         * 3.注册后返回一个SelectionKey,会和该Selector关联（集合）】
         * 4.Selector进行监听select?方法，返回有事件发生的通道的个数，
         * 5.进一步得到各个SelectionKey(有事件发生)
         * 6.在通过SelectionKey反向获取SocketChannel,方法channel(0
         * 7.可以通过得到的channel,完成业务处理
         */
    }
}
