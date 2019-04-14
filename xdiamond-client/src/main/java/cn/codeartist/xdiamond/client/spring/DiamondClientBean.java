package cn.codeartist.xdiamond.client.spring;

import cn.codeartist.xdiamond.client.net.NettyClient;
import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.client.spring.service.impl.NettyClientServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PreDestroy;
import java.io.Closeable;
import java.util.Properties;

/**
 * 配置中心客户端Bean
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public class DiamondClientBean implements InitializingBean, Closeable {

    final private Logger logger = LoggerFactory.getLogger(DiamondClientBean.class);

    final private NettyClient nettyClient = new NettyClient();

    final private NettyClientService nettyClientService;

    private Properties properties = new Properties();

    public DiamondClientBean() {
        nettyClientService = new NettyClientServiceImpl(properties);
    }

    @Override
    public void afterPropertiesSet() {
        try {
            logger.info("diamond client start...");
            nettyClient.connect(nettyClientService);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @PreDestroy
    @Override
    public void close() {
        logger.info("diamond client stop...");
        nettyClient.close();
    }

    public Properties getProperties() {
        return properties;
    }
}
