package handler;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import tcp.session.Session;
import tcp.utils.SessionUtil;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
@ChannelHandler.Sharable
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public static final LoginRequestHandler INSTANCE = new LoginRequestHandler();

    private LoginRequestHandler() {
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket msg) throws Exception {
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setPushId(msg.getPushId());
        try {
            SessionUtil.bindSession(new Session(msg.getPushId()), ctx.channel());
            loginResponsePacket.setSuccess(true);
            log.info("绑定pushId：{}", msg.getPushId());
        } catch (Exception e) {
            loginResponsePacket.setSuccess(false);
            loginResponsePacket.setReason(e.getMessage());
        } finally {
            ctx.writeAndFlush(loginResponsePacket);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        SessionUtil.unBindSession(ctx.channel());
    }
}
