package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import listener.ConnectionListener;
import listener.HeartBeatListener;
import tcp.operator.Pusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.response.LoginResponsePacket;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ConnectionListener connectionListener;
    private final HeartBeatListener heartBeatListener;

    public LoginResponseHandler(ConnectionListener connectionListener, HeartBeatListener heartBeatListener) {
        this.connectionListener = connectionListener;
        this.heartBeatListener = heartBeatListener;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket msg) throws Exception {
        if (connectionListener == null) {
            throw new RuntimeException("ConnectionListener is null");
        }
        if (msg.isSuccess()) {
            log.info("绑定pushId成功");
            connectionListener.success(new Pusher(ctx.channel()));
        } else {
            log.info("绑定pushId失败，失败原因[{}]", msg.getReason());
            connectionListener.fail(msg.getReason());
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("连接关闭");
        if (heartBeatListener != null) {
            heartBeatListener.onClose("服务端断开连接");
        }
    }
}
