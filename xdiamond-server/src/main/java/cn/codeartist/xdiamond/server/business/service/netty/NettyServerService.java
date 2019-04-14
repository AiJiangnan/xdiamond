package cn.codeartist.xdiamond.server.business.service.netty;


import cn.codeartist.xdiamond.common.net.bean.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

/**
 * Netty通讯服务器端服务
 *
 * @author 艾江南
 * @date 2019/4/13
 */
public interface NettyServerService {

    /**
     * 添加客户端管道
     *
     * @param channel 管道
     */
    void addChannel(Channel channel);

    /**
     * 发送消息
     *
     * @param message 消息
     */
    void sendMessage(String message);

    /**
     * 接收消息
     *
     * @param ctx     管道上下文
     * @param message 消息
     */
    void readMessage(ChannelHandlerContext ctx, Message message);
}
