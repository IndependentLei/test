package com.shebao.test.netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger= LoggerFactory.getLogger(NettyServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("Server channelRead... ");
        logger.info("Server received: {}", msg);
        ctx.write(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("Server channelReadComplete...");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("Server exceptionCaught...", cause);
        ctx.close();
    }
}