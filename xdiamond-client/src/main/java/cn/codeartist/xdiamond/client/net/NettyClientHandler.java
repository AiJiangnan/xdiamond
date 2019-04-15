package cn.codeartist.xdiamond.client.net;

import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.common.net.bean.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Netty客户端处理器
 *
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<Message> {

    final private NettyClientService nettyClientService;

    final private NettyClient nettyClient;

    NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        this.nettyClientService = nettyClient.getNettyClientService();
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
    public void channelUnregistered(ChannelHandlerContext ctx) {
        nettyClientService.channelUnregistered(ctx);
        nettyClient.channelRetryConnect(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }
}
