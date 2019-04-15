package cn.codeartist.xdiamond.client.spring.service;

import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;

/**
 * Netty通讯客户端服务
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public interface NettyClientService {

    /**
     * 注册管道
     *
     * @param channel 管道
     */
    void channelRegistered(Channel channel);

    /**
     * 激活管道
     *
     * @param channel 管道
     */
    void channelActive(Channel channel);

    /**
     * 发送一次请求，并返回响应
     *
     * @param request 请求
     * @return 异步响应
     */
    Future<?> sendMessage(Request request);

    /**
     * 读取响应消息
     *
     * @param ctx     管道上下文
     * @param message 消息
     */
    void readMessage(ChannelHandlerContext ctx, Message message);

    /**
     * 管道未注册
     *
     * @param ctx 管道上下文
     */
    void channelUnregistered(ChannelHandlerContext ctx);

    /**
     * 连接成功
     *
     * @param future 连接成功的结果
     */
    void channelConnected(ChannelFuture future);
}
