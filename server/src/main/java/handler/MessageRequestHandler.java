package handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;
import tcp.session.Session;
import tcp.utils.SessionUtil;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
@ChannelHandler.Sharable
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static MessageRequestHandler INSTANCE = new MessageRequestHandler();

    private MessageRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket msg) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromPushId(session.getPushId());
        messageResponsePacket.setMessage(msg.getMessage());
        Channel toUserChannel = SessionUtil.getChannel(msg.getToPushId());
        if (toUserChannel != null && SessionUtil.hasLogin(toUserChannel)) {
            toUserChannel.writeAndFlush(messageResponsePacket);
            log.info("[{}]发送消息,[{}]到[{}]", session.getPushId(), msg.getMessage(), msg.getToPushId());
        }
    }
}
