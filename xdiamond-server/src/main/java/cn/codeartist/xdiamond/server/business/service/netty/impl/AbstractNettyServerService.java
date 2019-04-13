package cn.codeartist.xdiamond.server.business.service.netty.impl;

import cn.codeartist.xdiamond.server.business.service.netty.NettyServerService;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author 艾江南
 * @date 2019/4/14
 */
public abstract class AbstractNettyServerService implements NettyServerService {

    final protected ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void addChannel(Channel channel) {
        channels.add(channel);
    }
}
