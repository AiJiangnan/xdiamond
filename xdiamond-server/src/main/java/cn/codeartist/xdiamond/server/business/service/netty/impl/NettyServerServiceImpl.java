package cn.codeartist.xdiamond.server.business.service.netty.impl;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Response;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author 艾江南
 * @date 2019/4/13
 */
@Service
public class NettyServerServiceImpl extends AbstractNettyServerService {

    final private Logger logger = LoggerFactory.getLogger(NettyServerServiceImpl.class);

    @Override
    public void sendMessage(String message) {
        logger.debug("send message to clients: {}", message);
        Response response = Response.builder().data("test", message).build();
        channels.forEach(channel -> channel.writeAndFlush(Message.response().json(response).build()));
    }

    @Override
    public void readMessage(ChannelHandlerContext ctx, Message message) {
        logger.debug("read message from client: {}", message.responseOf().dataValue("test"));
        ctx.writeAndFlush(Message.response().json(Response.builder().data("test", message.responseOf().dataValue("test")).build()).build());
    }
}
