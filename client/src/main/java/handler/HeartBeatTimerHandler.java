package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.request.HeartBeatRequestPacket;

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
                ctx.writeAndFlush(new HeartBeatRequestPacket());
            }
        }, HEART_BEAT_INTERVAL, HEART_BEAT_INTERVAL, TimeUnit.SECONDS);
    }
}
