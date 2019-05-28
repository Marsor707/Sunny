package handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import listener.HeartBeatListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class IMIdleStateHandler extends IdleStateHandler {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private static final int READER_IDLE_TIME = 15;
    private final HeartBeatListener heartBeatListener;

    public IMIdleStateHandler(HeartBeatListener heartBeatListener) {
        super(READER_IDLE_TIME, 0, 0, TimeUnit.SECONDS);
        this.heartBeatListener = heartBeatListener;
    }

    /**
     * 只在socket未关闭的情况下，达成条件才会触发，socket关闭后再也不会触发
     */
    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        log.info("{}秒内未读到数据，关闭连接", READER_IDLE_TIME);
        ctx.channel().close();
        if (heartBeatListener != null) {
            heartBeatListener.onClose(evt.state().name());
        }
    }
}
