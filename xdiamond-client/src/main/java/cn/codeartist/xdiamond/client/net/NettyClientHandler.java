package cn.codeartist.xdiamond.client.net;

import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.common.net.bean.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Netty客户端处理器
 *
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    final private Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);

    final private NettyClientService nettyClientService;

    NettyClientHandler(NettyClientService nettyClientService) {
        this.nettyClientService = nettyClientService;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        nettyClientService.channelRegistered(ctx.channel());
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        nettyClientService.channelActive(ctx.channel());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Message msg) {
        nettyClientService.readMessage(ctx, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("server is unexpected disconnected.", cause);
        ctx.close();
    }
}
