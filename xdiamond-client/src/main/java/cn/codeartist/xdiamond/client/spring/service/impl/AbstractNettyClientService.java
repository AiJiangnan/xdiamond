package cn.codeartist.xdiamond.client.spring.service.impl;

import cn.codeartist.xdiamond.client.spring.service.NettyClientService;
import cn.codeartist.xdiamond.common.enums.MessageType;
import cn.codeartist.xdiamond.common.net.bean.Message;
import cn.codeartist.xdiamond.common.net.bean.Request;
import cn.codeartist.xdiamond.common.net.bean.Response;
import cn.codeartist.xdiamond.common.util.BaseThreadFactory;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
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

    final protected Logger logger = LoggerFactory.getLogger(AbstractNettyClientService.class);

    final private Timer timer = new HashedWheelTimer(BaseThreadFactory.builder().namingPattern("xdiamond-clean-%d").daemon(true).build());

    final private AtomicInteger id = new AtomicInteger(1);

    private final Map<Integer, Promise<Response>> promiseMap = new ConcurrentHashMap<>();

    private Channel channel;

    @Override
    public void channelRegistered(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void channelActive(Channel channel) {

    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) {
        channel = null;
    }

    @Override
    public Response sendMessage(Request request) {
        if (channel == null || !channel.isActive()) {
            throw new ChannelException("channel is not available");
        }
        Promise<Response> promise = new DefaultPromise<>(channel.eventLoop());
        final int requestId = id.getAndIncrement();
        request.setId(requestId);
        promiseMap.put(requestId, promise);
        promise.addListener(future -> promiseMap.remove(requestId));
        timer.newTimeout(timeout -> {
            Promise<Response> remove = promiseMap.remove(requestId);
            if (remove != null && !remove.isDone()) {
                remove.setFailure(new TimeoutException("no response from server, timeout!"));
            }
        }, 30, TimeUnit.SECONDS);
        logger.debug("request to server: {}", request);
        channel.writeAndFlush(Message.request().json(request).build());
        try {
            Response response = promise.get(10, TimeUnit.SECONDS);
            if (promise.isSuccess()) {
                return response;
            }
            logger.error("xdiamond response is error.");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void readMessage(ChannelHandlerContext ctx, Message message) {
        if (message.getType() == MessageType.RESPONSE.code()) {
            Response response = message.responseOf();
            logger.debug("response from server: {}", response);
            Promise<Response> promise = promiseMap.get(response.getId());
            if (!response.isSuccess()) {
                promise.setFailure(new RuntimeException(response.getError()));
                return;
            }
            if (promise != null && !promise.isDone()) {
                promise.setSuccess(response);
            }
        }
    }
}
