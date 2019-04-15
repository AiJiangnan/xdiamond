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
import io.netty.util.concurrent.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.ConnectException;
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

    final protected Logger logger = LoggerFactory.getLogger(AbstractNettyClientService.class);

    final private Timer timer = new HashedWheelTimer(BaseThreadFactory.builder().namingPattern("").daemon(true).build());

    final private AtomicInteger id = new AtomicInteger(1);

    final protected Map<Integer, Promise<?>> promiseMap = new ConcurrentHashMap<>();

    final private DefaultEventExecutorGroup eventExecutors = new DefaultEventExecutorGroup(1,
            BaseThreadFactory.builder().namingPattern("xdiamond-%d").daemon(true).build());

    private Channel channel;

    @Override
    public void channelRegistered(Channel channel) {
        this.channel = channel;
        logger.debug("channel registered.");
    }

    @Override
    public void channelActive(Channel channel) {
        logger.debug("channel active.");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        channel = null;
        logger.debug("channel unregistered.");
    }

    @Override
    public Future<?> sendMessage(Request request) {
        if (channel == null || !channel.isActive()) {
            return new FailedFuture<>(eventExecutors.next(), new ConnectException("channel is not available"));
        }
        Promise<?> promise = new DefaultPromise<>(channel.eventLoop());
        final int requestId = id.getAndIncrement();
        request.setId(requestId);
        promiseMap.put(requestId, promise);
        promise.addListener(future -> promiseMap.remove(requestId));
        timer.newTimeout(timeout -> {
            Promise<?> remove = promiseMap.remove(requestId);
            if (remove != null && !remove.isDone()) {
                remove.setFailure(new TimeoutException("no response from server, timeout!"));
            }
        }, 30, TimeUnit.SECONDS);
        logger.debug("request to server: {}", request);
        channel.writeAndFlush(Message.request().json(request).build());
        return promise;
    }

    @Override
    public void readMessage(ChannelHandlerContext ctx, Message message) {
        if (message.getType() == MessageType.RESPONSE.code()) {
            Response response = message.responseOf();
            logger.debug("response from server: {}", response);
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
