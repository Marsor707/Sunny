package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.request.CheckOnlineStateRequestPacket;
import protocol.response.CheckOnlineStateResponsePacket;
import tcp.utils.SessionUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
@ChannelHandler.Sharable
public class CheckOnlineStateRequestHandler extends SimpleChannelInboundHandler<CheckOnlineStateRequestPacket> {

    public static CheckOnlineStateRequestHandler INSTANCE = new CheckOnlineStateRequestHandler();

    private CheckOnlineStateRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CheckOnlineStateRequestPacket msg) throws Exception {
        Map<String, Boolean> onlineStateMap = new HashMap<>();
        for (String pushId : msg.getPushIds()) {
            onlineStateMap.put(pushId, SessionUtil.hasLogin(pushId));
        }
        CheckOnlineStateResponsePacket responsePacket = new CheckOnlineStateResponsePacket();
        responsePacket.setOnlineStateMap(onlineStateMap);
        ctx.writeAndFlush(responsePacket);
    }
}
