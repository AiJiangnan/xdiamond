package cn.codeartist.xdiamond.client.spring.service.impl;

import cn.codeartist.xdiamond.client.spring.DiamondProperties;
import cn.codeartist.xdiamond.common.entity.ConfigMessage;
import cn.codeartist.xdiamond.common.enums.Command;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFuture;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Netty通讯客户端服务
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public class NettyClientServiceImpl extends AbstractNettyClientService {

    private DiamondProperties diamondProperties;

    private Properties properties;

    public NettyClientServiceImpl(DiamondProperties diamondProperties, Properties properties) {
        this.diamondProperties = diamondProperties;
        this.properties = properties;
    }

    @Override
    public void channelConnected(ChannelFuture future) {
        try {
            boolean await = future.await(10, TimeUnit.SECONDS);
            if (await && future.isSuccess()) {
                loadServerConfig();
            }
            if (!future.isSuccess()) {
                logger.error("xdiamond connect server error. {}", diamondProperties.getProjectInfo(), future.cause());
            }
        } catch (Exception e) {
            logger.error("xdiamond connect server error. {}", diamondProperties.getProjectInfo(), e);
        }
    }

    @Override
    public void loadServerConfig() {
        boolean loadLocalConfig = true;
        Request request = Request.builder().command(Command.GET_CONFIG)
                .data("groupId", diamondProperties.getGroupId())
                .data("artifactId", diamondProperties.getArtifactId())
                .data("version", diamondProperties.getVersion())
                .data("profile", diamondProperties.getProfile())
                .build();
        Response response = sendMessage(request);
        Command command = Command.of(response.getCommand());
        if (command != null && command == Command.GET_CONFIG && response.getData() != null) {
            loadConfig(JSON.parseObject((String) response.getData().get("configs"), ConfigMessage.class));
            logger.info("xdiamond load config from server success. {}", diamondProperties.getProjectInfo());
            loadLocalConfig = false;
        }
        if (loadLocalConfig) {
            // TODO: 2019/4/15 艾江南 加载本地缓存
        }
    }

    private void loadConfig(ConfigMessage configMessage) {
        logger.debug("load config from server: {}", configMessage);
        // TODO: 2019/4/15 艾江南 缓存本地
        Optional.ofNullable(configMessage).map(ConfigMessage::getConfigs).orElse(new ArrayList<>())
                .forEach(config -> properties.put(config.getKey(), config.getValue()));
    }
}
