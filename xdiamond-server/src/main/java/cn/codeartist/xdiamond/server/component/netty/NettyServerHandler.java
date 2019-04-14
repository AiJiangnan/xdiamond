package cn.codeartist.xdiamond.server.component.netty;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.server.business.service.netty.NettyServerService;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty服务端处理器
 *
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<Message> {

    final private Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    final private NettyServerService nettyServerService;

    NettyServerHandler(NettyServerService nettyServerService) {
        this.nettyServerService = nettyServerService;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        logger.debug("a client has connected.");
        nettyServerService.addChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        nettyServerService.readMessage(ctx, msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("client unexpected disconnected.", cause);
        ctx.close();
    }
}
