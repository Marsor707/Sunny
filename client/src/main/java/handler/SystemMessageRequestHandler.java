package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.SystemMessageRequestPacket;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class SystemMessageRequestHandler extends SimpleChannelInboundHandler<SystemMessageRequestPacket> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public SystemMessageRequestHandler(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    private final MessageListener messageListener;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, SystemMessageRequestPacket msg) throws Exception {
        log.info("收到系统消息:{}", msg.getMessage());
        if (messageListener != null) {
            messageListener.onSystemTextMessageReceived(msg.getMessage());
        }
    }
}
