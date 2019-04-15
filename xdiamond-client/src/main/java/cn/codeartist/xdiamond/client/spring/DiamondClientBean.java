package cn.codeartist.xdiamond.client.spring;

import cn.codeartist.xdiamond.client.net.NettyClient;
import io.netty.channel.ChannelFuture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Properties properties = new Properties();

    @Autowired
    private DiamondProperties diamondProperties;

    @Override
    public void afterPropertiesSet() {
        try {
            logger.info("diamond client start...");
            ChannelFuture future = nettyClient.configure(diamondProperties, properties).connect();
            nettyClient.futureHandler(future);
        } catch (Exception e) {
            logger.error("xdiamond load config error. {}", diamondProperties.getProjectInfo(), e);
        }
    }

    @PreDestroy
    @Override
    public void close() {
        nettyClient.close();
        logger.info("diamond client stop...");
    }

    public Properties getProperties() {
        return properties;
    }
}
