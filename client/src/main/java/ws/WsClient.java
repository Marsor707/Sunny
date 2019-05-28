package ws;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.EmptyHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.handler.HeartBeatTimerHandler;
import ws.handler.WsClientHandler;
import ws.listener.ConnectionListener;
import ws.listener.MessageListener;
import ws.operator.Pusher;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class WsClient {

    private final String wsUri;
    private final ConnectionListener connectionListener;
    private final Logger log = LoggerFactory.getLogger(getClass());
    private MessageListener messageListener;

    public WsClient(String uri, ConnectionListener connectionListener) {
        this.wsUri = uri;
        this.connectionListener = connectionListener;
    }

    public void addMessageListener(MessageListener listener) {
        this.messageListener = listener;
    }

    public void connect() {
        EventLoopGroup workGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        URI uri = null;
        try {
            uri = new URI(wsUri);
        } catch (URISyntaxException e) {
            log.error("uri不合法");
            return;
        }
        WebSocketClientHandshaker handshaker = WebSocketClientHandshakerFactory
                .newHandshaker(uri, WebSocketVersion.V13, null, true, EmptyHttpHeaders.INSTANCE);
        final WsClientHandler wsClientHandler = new WsClientHandler(handshaker, messageListener);
        b.group(workGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new HttpClientCodec())
                                .addLast(new HttpObjectAggregator(65536))
                                .addLast(wsClientHandler)
                                .addLast(new HeartBeatTimerHandler());
                    }
                });

        try {
            b.connect(uri.getHost(), uri.getPort()).sync();
        } catch (InterruptedException e) {
            log.error("连接失败");
            return;
        }
        wsClientHandler.handShakeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    connectionListener.success(new Pusher(future.channel()));
                    log.info("连接成功");
                } else {
                    log.error("连接失败");
                    connectionListener.fail("连接失败");
                }
            }
        });
    }
}
