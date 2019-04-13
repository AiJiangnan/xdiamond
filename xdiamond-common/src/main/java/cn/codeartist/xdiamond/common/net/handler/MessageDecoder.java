package cn.codeartist.xdiamond.common.net.handler;

import cn.codeartist.xdiamond.common.net.bean.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * 消息解码器
 *
 * @author 艾江南
 * @date 2019/4/13
 */
public class MessageDecoder extends ByteToMessageDecoder {

    private int state = 0;
    private int dataLength = 0;
    private Message msg;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) {
        if (state == 0 && byteBuf.readableBytes() >= 8) {
            msg = new Message();
            msg.setVersion(byteBuf.readShort());
            dataLength = byteBuf.readInt() - 2;
            msg.setType(byteBuf.readShort());
            state = 1;
        }
        if (state == 1 && byteBuf.readableBytes() >= dataLength) {
            byte[] data = new byte[dataLength];
            byteBuf.readBytes(data);
            msg.setData(data);
            out.add(msg);
            dataLength = 0;
            state = 0;
        }
    }
}
