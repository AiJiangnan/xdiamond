package cn.codeartist.xdiamond.server.business.service.netty.impl;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import cn.codeartist.xdiamond.server.business.service.netty.NettyServerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * Netty通讯服务器端服务
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public abstract class AbstractNettyServerService implements NettyServerService {

    final protected ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void addChannel(Channel channel) {
        channels.add(channel);
    }

    @Override
    public void readMessage(ChannelHandlerContext ctx, Message message) {
        ctx.writeAndFlush(Message.response().json(acceptMessage(message.requestOf())).build());
    }

    /**
     * 处理请求响应
     *
     * @param request 请求
     * @return 响应
     */
    abstract protected Response acceptMessage(Request request);
}
