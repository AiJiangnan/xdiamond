package cn.codeartist.xdiamond.server.business.service.netty.impl;

import cn.codeartist.xdiamond.common.entity.Config;
import cn.codeartist.xdiamond.common.entity.ConfigMessage;
import cn.codeartist.xdiamond.common.enums.Command;
import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import cn.codeartist.xdiamond.common.net.bean.Response.ResponseBuilder;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        Command command = Command.of(request.getCommand());
        ResponseBuilder builder = Response.success();
        builder.id(request.getId())
                .command(command);
        if (Command.GET_CONFIG == command) {
            // TODO: 2019/4/15 艾江南 客户端启动第一次获取配置
            logger.debug("request: {}", request);
            ConfigMessage message = new ConfigMessage();
            List<Config> configs = new ArrayList<>();
            Config config = new Config();
            config.setId(1L);
            config.setKey("name");
            config.setValue("艾江南");
            configs.add(config);
            message.setConfigs(configs);
            return builder.data("configs", JSON.toJSONString(message)).build();
        }
        return Response.success().data("response", "I am a response.").build();
    }
}
