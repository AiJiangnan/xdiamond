package cn.codeartist.xdiamond.server.business.service.netty.impl;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Netty通讯服务器端服务
 *
 * @author 艾江南
 * @date 2019/4/13
 */
@Service
public class NettyServerServiceImpl extends AbstractNettyServerService {

    final private Logger logger = LoggerFactory.getLogger(NettyServerServiceImpl.class);

    @Async
    @Override
    public void sendMessage(String message) {
        logger.debug("send message to clients: {}", message);
        Response response = Response.success().data("response", message).build();
        channels.forEach(channel -> channel.writeAndFlush(Message.response().json(response).build()));
    }

    @Override
    protected Response acceptMessage(Request request) {
        logger.debug("I am a request: {}", request.dataValue("request"));
        return Response.success().data("response", "I am a response.").build();
    }
}
