package cn.codeartist.xdiamond.client.net;

import cn.codeartist.xdiamond.common.net.handler.MessageDecoder;
import cn.codeartist.xdiamond.common.net.handler.MessageEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;

/**
 * Netty客户端
 *
 * @author 艾江南
 * @date 2019/4/12
 */
public class NettyClient implements Closeable {

    final private Logger logger = LoggerFactory.getLogger(NettyClient.class);

    private String host = "127.0.0.1";
    private Integer port = 5678;

    private EventLoopGroup group = new NioEventLoopGroup();

    public void connect() throws InterruptedException {
        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(nettyChannelInitializer())
                .connect(host, port)
                .sync()
                .channel()
                .closeFuture()
                .sync()
                .addListener(future -> close());
        logger.debug("netty client started");
    }

    private ChannelInitializer<SocketChannel> nettyChannelInitializer() {
        return new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ch.pipeline().addLast(
                        new MessageDecoder(),
                        new MessageEncoder(),
                        new NettyClientHandler());
            }
        };
    }

    @Override
    public void close() {
        group.shutdownGracefully();
    }
}
