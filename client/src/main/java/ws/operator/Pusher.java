package ws.operator;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

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

    public void sendToServer(String message) {
        TextWebSocketFrame tws = new TextWebSocketFrame(message);
        channel.writeAndFlush(tws);
    }
}
