package cn.codeartist.xdiamond.server.component.netty;

import cn.codeartist.xdiamond.common.net.handler.MessageDecoder;
import cn.codeartist.xdiamond.common.net.handler.MessageEncoder;
import cn.codeartist.xdiamond.server.business.service.netty.NettyServerService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.Closeable;

/**
 * Netty服务器
 *
 * @author 艾江南
 * @date 2019/4/12
 */
@Configuration
public class NettyServer implements Closeable {

    final private Logger logger = LoggerFactory.getLogger(NettyServer.class);

    @Value("${netty.server.port:5678}")
    private Integer port = 5678;

    @Autowired
    private NettyServerService nettyServerService;

    private EventLoopGroup bossGroup = new NioEventLoopGroup();
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    @PostConstruct
    public void init() throws InterruptedException {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(nettyChannelInitializer())
                .bind(port)
                .sync()
                .channel()
                .closeFuture()
                .addListener(future -> close());
        logger.debug("netty server started");
    }

    private ChannelInitializer<SocketChannel> nettyChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(
                        new MessageDecoder(),
                        new MessageEncoder(),
                        new NettyServerHandler(nettyServerService));
            }
        };
    }

    @PreDestroy
    @Override
    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
