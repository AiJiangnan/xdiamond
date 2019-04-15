package cn.codeartist.xdiamond.client.spring.service.impl;

import cn.codeartist.xdiamond.client.spring.DiamondProperties;
import cn.codeartist.xdiamond.common.entity.ConfigMessage;
import cn.codeartist.xdiamond.common.enums.Command;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import com.alibaba.fastjson.JSON;
import io.netty.channel.ChannelFuture;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.Promise;

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

    @SuppressWarnings("unchecked")
    @Override
    public void channelConnected(ChannelFuture future) {
        boolean loadLocalConfig = true;
        try {
            boolean await = future.await(10, TimeUnit.SECONDS);
            if (await && future.isSuccess()) {
                Request request = Request.builder()
                        .command(Command.GET_CONFIG)
                        .build();
                Future<ConfigMessage> messageFuture = (Future<ConfigMessage>) sendMessage(request);
                ConfigMessage configMessage = messageFuture.get(10, TimeUnit.SECONDS);
                if (messageFuture.isSuccess()) {
                    loadConfig(configMessage);
                    logger.info("load config from xdiamond server success. {}", diamondProperties.getProjectInfo());
                    loadLocalConfig = false;
                }
            }
            if (!future.isSuccess()) {
                logger.error("load config from xdiamond server error. {}", diamondProperties.getProjectInfo(), future.cause());
            }
        } catch (Exception e) {
            logger.error("load config from xdiamond server error. {}", diamondProperties.getProjectInfo(), e);
        }
        if (loadLocalConfig) {
            // TODO: 2019/4/15 艾江南 加载本地缓存
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void acceptMessage(Response response) {
        Command command = Command.of(response.getCommand());
        if (command == Command.GET_CONFIG) {
            logger.debug("get configs: {}", response.dataValue("configs"));
            Optional.ofNullable(response.dataValue("configs")).map(config -> JSON.parseObject((String) config, ConfigMessage.class)).ifPresent(configMessage -> {
                Promise<ConfigMessage> promise = (Promise<ConfigMessage>) promiseMap.get(response.getId());
                if (promise != null && !promise.isDone()) {
                    promise.setSuccess(configMessage);
                }
            });
        } else if (command == Command.CONFIG_CHANGED) {

        } else if (command == Command.HEAR_BEAT) {

        }
    }

    private void loadConfig(ConfigMessage configMessage) {
        logger.debug("load config from server: {}", configMessage);
        Optional.ofNullable(configMessage).map(ConfigMessage::getConfigs).orElse(new ArrayList<>())
                .forEach(config -> properties.put(config.getKey(), config.getValue()));
    }
}
