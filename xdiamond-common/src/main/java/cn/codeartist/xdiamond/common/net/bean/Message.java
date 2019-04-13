package cn.codeartist.xdiamond.common.net.bean;

import cn.codeartist.xdiamond.common.enums.MessageType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.Data;

/**
 * Netty 通讯消息
 *
 * @author 艾江南
 * @date 2019/4/13
 */
@Data
public class Message {

    /**
     * 版本
     */
    private short version = 1;
    /**
     * 消息类型
     *
     * @see cn.codeartist.xdiamond.common.enums.MessageType
     */
    private short type;
    /**
     * 消息体
     */
    private byte[] data;

    public static MessageBuilder notice() {
        return builder().type(MessageType.NOTICE);
    }

    public static MessageBuilder request() {
        return builder().type(MessageType.REQUEST);
    }

    public static MessageBuilder response() {
        return builder().type(MessageType.RESPONSE);
    }

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public static class MessageBuilder {
        private Message message;

        public MessageBuilder() {
            message = new Message();
        }

        public MessageBuilder version(short version) {
            message.setVersion(version);
            return this;
        }

        public MessageBuilder type(MessageType type) {
            message.setType(type.code());
            return this;
        }

        public MessageBuilder data(byte[] data) {
            message.setData(data);
            return this;
        }

        public MessageBuilder json(Object object) {
            message.setData(JSON.toJSONBytes(object, SerializerFeature.DisableCircularReferenceDetect));
            return this;
        }

        public Message build() {
            return message;
        }
    }

    public Response responseOf() {
        return JSON.parseObject(data, Response.class);
    }

    public Request requestOf() {
        return JSON.parseObject(data, Request.class);
    }

    public Notice noticeOf() {
        return JSON.parseObject(data, Notice.class);
    }
}
