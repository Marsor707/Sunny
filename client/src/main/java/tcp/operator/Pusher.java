package tcp.operator;

import handler.CheckOnlineStateResponseHandler;
import io.netty.channel.Channel;
import listener.CheckOnlineStateResponseListener;
import protocol.request.CheckOnlineStateRequestPacket;
import protocol.request.MessageRequestPacket;

import java.util.List;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class Pusher {

    private final Channel channel;

    public Pusher(Channel channel) {
        this.channel = channel;
    }

    public void sendSingleTextMessage(String msg, String pushId) {
        MessageRequestPacket messageRequestPacket = new MessageRequestPacket(pushId, msg);
        channel.writeAndFlush(messageRequestPacket);
    }

    public void checkOnlineState(List<String> pushIds, CheckOnlineStateResponseListener listener) {
        CheckOnlineStateRequestPacket requestPacket = new CheckOnlineStateRequestPacket();
        requestPacket.setPushIds(pushIds);
        CheckOnlineStateResponseHandler.listener = listener;
        channel.writeAndFlush(requestPacket);
    }
}
