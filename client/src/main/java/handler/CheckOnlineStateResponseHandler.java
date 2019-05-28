package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import listener.CheckOnlineStateResponseListener;
import protocol.response.CheckOnlineStateResponsePacket;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class CheckOnlineStateResponseHandler extends SimpleChannelInboundHandler<CheckOnlineStateResponsePacket> {

    public static CheckOnlineStateResponseListener listener;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CheckOnlineStateResponsePacket msg) throws Exception {
        if (listener != null) {
            listener.checkOnlineStateResponse(msg.getOnlineStateMap());
        }
    }
}
