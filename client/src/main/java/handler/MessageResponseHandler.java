package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.MessageResponsePacket;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final MessageListener messageListener;

    public MessageResponseHandler(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket msg) throws Exception {
        log.info("从[{}],收到消息,[{}]", msg.getFromPushId(), msg.getMessage());
        if (messageListener != null) {
            messageListener.onSingleTextMessageReceived(msg.getFromPushId(), msg.getMessage());
        }
    }
}
