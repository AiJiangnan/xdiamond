package cn.codeartist.xdiamond.client.net;

import cn.codeartist.xdiamond.client.spring.DiamondProperties;
import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.client.spring.service.impl.NettyClientServiceImpl;
import cn.codeartist.xdiamond.common.net.handler.MessageDecoder;
import cn.codeartist.xdiamond.common.net.handler.MessageEncoder;
import cn.codeartist.xdiamond.common.util.BaseThreadFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Netty客户端
 *
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyClient implements Closeable {

    final private Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host;
    private Integer port;
    private Integer retryIntervalSeconds;
    private Integer retryTimes = 0;
    private Integer maxRetryTimes;

    private EventLoopGroup group = new NioEventLoopGroup(0,
            BaseThreadFactory.builder().namingPattern("xdiamond-%d").daemon(true).build());

    private Bootstrap bootstrap = new Bootstrap();

    private NettyClientService nettyClientService;

    public void futureHandler(ChannelFuture future) {
        nettyClientService.channelConnected(future);
    }

    public ChannelFuture connect() {
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true);
        return configureBootstrap(bootstrap, group).connect();
    }

    public NettyClient configure(DiamondProperties diamondProperties, Properties properties) {
        this.host = diamondProperties.getHost();
        this.port = diamondProperties.getPort();
        this.retryIntervalSeconds = diamondProperties.getRetryIntervalSeconds();
        this.maxRetryTimes = diamondProperties.getMaxRetryTimes();
        this.nettyClientService = new NettyClientServiceImpl(diamondProperties, properties);
        return this;
    }

    public void channelRetryConnect(ChannelHandlerContext ctx) {
        retryTimes++;
        if (retryTimes > maxRetryTimes) {
            return;
        }
        EventLoop eventLoop = ctx.channel().eventLoop();
        eventLoop.schedule(() -> {
            logger.info("reconnect to {}:{}", host, port);
            configureBootstrap(new Bootstrap(), eventLoop).connect().addListener(future -> {
                if (future.isSuccess()) {
                    logger.info("connect to {}:{} successful.", host, port);
                } else {
                    logger.error("connect to {}:{} error.", host, port, future.cause());
                }
            });
        }, retryIntervalSeconds, TimeUnit.SECONDS);
    }

    private Bootstrap configureBootstrap(Bootstrap b, EventLoopGroup g) {
        return b.group(g)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .handler(nettyChannelInitializer());
    }

    private ChannelInitializer<SocketChannel> nettyChannelInitializer() {
        NettyClient nettyClient = this;
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(
                        new MessageDecoder(),
                        new MessageEncoder(),
                        new NettyClientHandler(nettyClient));
            }
        };
    }

    public NettyClientService getNettyClientService() {
        return nettyClientService;
    }

    @Override
    public void close() {
        group.shutdownGracefully();
    }
}
