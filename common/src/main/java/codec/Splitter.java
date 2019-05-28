package codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import protocol.PacketCodeC;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class Splitter extends LengthFieldBasedFrameDecoder {


    // 我们自定义的数据包格式 魔数(4字节)+版本号(1字节)+序列化算法(1字节)+指令(1字节)+数据长度n(4字节)+数据(n字节)
    private static final int LENGTH_FIELD_OFFSET = 7;
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Splitter() {
        super(Integer.MAX_VALUE, LENGTH_FIELD_OFFSET, LENGTH_FIELD_LENGTH);
    }

    @Override
    protected Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        if (in.getInt(in.readerIndex()) != PacketCodeC.MAGIC_NUMBER) {
            //不是本协议的数据包 断开连接
            ctx.channel().close();
            return null;
        }
        return super.decode(ctx, in);
    }
}
