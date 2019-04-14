package cn.codeartist.xdiamond.client.spring.service.impl;

import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.common.enums.MessageType;
import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import cn.codeartist.xdiamond.common.util.BaseThreadFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timer;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Netty通讯客户端服务
 *
 * @author 艾江南
 * @date 2019/4/14
 */
public abstract class AbstractNettyClientService implements NettyClientService {

    final protected Timer timer = new HashedWheelTimer(BaseThreadFactory.builder().namingPattern("").daemon(true).build());

    final protected AtomicInteger id = new AtomicInteger();

    final protected Map<Integer, Promise<?>> promiseMap = new ConcurrentHashMap<>();

    final protected Logger logger = LoggerFactory.getLogger(AbstractNettyClientService.class);

    protected Channel channel;

    @Override
    public void channelRegistered(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelActive(Channel channel) {

    }

    @Override
    public Response sendMessage(Request request) {
//        final int requestId = id.getAndIncrement();
//        Promise<?> promise = channel.writeAndFlush(Message.builder().json(request).build()).channel().newProgressivePromise();
//        promiseMap.put(requestId, promise);
//        promise.addListener(future -> {
//
//        });
//        promise.addListener(future -> promiseMap.remove(requestId));

        DefaultPromise<?> promise = new DefaultPromise<>(channel.eventLoop());
        final int requestId = id.getAndIncrement();
        promiseMap.put(requestId, promise);
        promise.addListener(future -> promiseMap.remove(requestId));
        timer.newTimeout(timeout -> {
            Promise<?> remove = promiseMap.remove(requestId);
            if (remove != null && !remove.isDone()) {
                remove.setFailure(new TimeoutException("no response from server, timeout!"));
            }
        }, 30, TimeUnit.SECONDS);
//        return promise;
        return null;
    }

    @Override
    public void readMessage(ChannelHandlerContext ctx, Message message) {
        if (message.getType() == MessageType.RESPONSE.code()) {
            Response response = message.responseOf();
            if (!response.isSuccess()) {
                Promise<?> promise = promiseMap.get(response.getId());
                promise.setFailure(new RuntimeException(response.getError()));
                return;
            }
            acceptMessage(response);
        }
    }

    /**
     * 处理响应消息
     *
     * @param response 响应
     */
    abstract protected void acceptMessage(Response response);
}
