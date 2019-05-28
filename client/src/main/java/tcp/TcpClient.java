package tcp;

import codec.PacketCodecHandler;
import codec.Splitter;
import handler.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import listener.ConnectionListener;
import listener.HeartBeatListener;
import listener.MessageListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import protocol.request.LoginRequestPacket;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class TcpClient {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ConnectionListener connectionListener;
    private final String HOST;
    private final Integer PORT;
    private final String pushId;
    private MessageListener messageListener;
    private HeartBeatListener heartBeatListener;

    public TcpClient(String HOST, Integer PORT, String pushId, ConnectionListener connectionListener) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.pushId = pushId;
        this.connectionListener = connectionListener;
    }

    public void addMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    public void addHeartBeatListener(HeartBeatListener heartBeatListener) {
        this.heartBeatListener = heartBeatListener;
    }

    public void connect() {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler(heartBeatListener));
                        pipeline.addLast(new Splitter());
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        pipeline.addLast(new LoginResponseHandler(connectionListener, heartBeatListener));
                        pipeline.addLast(new MessageResponseHandler(messageListener));
                        pipeline.addLast(new SystemMessageRequestHandler(messageListener));
                        pipeline.addLast(new CheckOnlineStateResponseHandler());
                        pipeline.addLast(new HeartBeatTimerHandler());
                    }
                });
        connect(b, HOST, PORT);
    }

    private void connect(Bootstrap b, String host, Integer port) {
        b.connect(host, port).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("连接成功，地址{}，端口{}", HOST, PORT);
                    //绑定pushId与channel
                    Channel channel = ((ChannelFuture) future).channel();
                    channel.writeAndFlush(new LoginRequestPacket(pushId));
                } else {
                    log.error("连接失败，地址{}，端口{}", HOST, PORT);
                }
            }
        });
    }
}
