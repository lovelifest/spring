package com.funtl.st.demo.netty.inboundhandlerandoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author songtao
 * @create 2020-04-2020/4/25-21:25
 */
public class MyLongToByteEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println(" MyLongToByteEncoder encode 被调用 ");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}
