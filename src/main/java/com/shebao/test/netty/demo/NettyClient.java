package com.shebao.test.netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyClient {

    private static final Logger logger = LoggerFactory.getLogger(NettyClient.class);

    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8080"));

    public static void main(String[] args) throws Exception {
        // 创建EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // 创建Bootstrap
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(new NettyClientHandler());
                        }
                    })
                    .option(ChannelOption.SO_KEEPALIVE, true);

            // 链接服务器
            ChannelFuture f = b.connect(HOST, PORT).sync();

            // 发送消息给服务器
            f.channel().writeAndFlush("Hello Netty Server!");

            // 等待链接关闭
            f.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            group.shutdownGracefully();
        }
    }
}

