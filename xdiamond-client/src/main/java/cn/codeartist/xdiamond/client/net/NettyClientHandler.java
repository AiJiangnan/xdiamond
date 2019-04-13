package cn.codeartist.xdiamond.client.net;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    final private Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Request request = Request.builder().data("test", "Hello message").build();
        ctx.writeAndFlush(Message.request().json(request).build());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        logger.info("netty client receive body: {}", msg.requestOf().dataValue("test"));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
