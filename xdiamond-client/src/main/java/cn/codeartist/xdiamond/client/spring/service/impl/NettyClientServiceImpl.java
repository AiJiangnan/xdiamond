package cn.codeartist.xdiamond.client.spring.service.impl;

import cn.codeartist.xdiamond.common.net.bean.Response;

import java.util.Properties;

/**
 * Netty通讯客户端服务
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public class NettyClientServiceImpl extends AbstractNettyClientService {

    private Properties properties;

    public NettyClientServiceImpl(Properties properties) {
        this.properties = properties;
    }

    @Override
    protected void acceptMessage(Response response) {
        logger.debug("netty client receive data: {}", response.dataValue("response"));
        response.getData().forEach((k, v) -> {
            properties.put(k, v);
//            System.setProperty(k, String.valueOf(v));
        });
    }
}
