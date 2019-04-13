package cn.codeartist.xdiamond.common.net.handler;

import cn.codeartist.xdiamond.common.net.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * 消息编码器
 *
 * @author 艾江南
 * @date 2019/4/13
 */
public class MessageEncoder extends MessageToByteEncoder<Message> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Message msg, ByteBuf out) {
        out.writeShort(msg.getVersion());
        out.writeInt(msg.getData().length + 2);
        out.writeShort(msg.getType());
        out.writeBytes(msg.getData());
        ctx.flush();
    }
}
