package ws.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.TimeUnit;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {

    private static final Integer HEART_BEAT_INTERVAL = 5;

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        ctx.executor().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ctx.writeAndFlush(new TextWebSocketFrame());
            }
        }, HEART_BEAT_INTERVAL, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
