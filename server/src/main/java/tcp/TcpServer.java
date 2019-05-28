package tcp;

import codec.PacketCodecHandler;
import codec.Splitter;
import handler.IMIdleStateHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import handler.CheckOnlineStateRequestHandler;
import handler.HeartBeatRequestHandler;
import handler.LoginRequestHandler;
import handler.MessageRequestHandler;
import listener.BindListener;
import tcp.operator.Operator;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class TcpServer {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final Integer PORT;
    private final BindListener bindListener;

    public TcpServer(Integer port, BindListener bindListener) {
        PORT = port;
        this.bindListener = bindListener;
    }

    @SuppressWarnings("Duplicates")
    public void run() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new IMIdleStateHandler(null));
                        pipeline.addLast(new Splitter());
                        pipeline.addLast(PacketCodecHandler.INSTANCE);
                        pipeline.addLast(HeartBeatRequestHandler.INSTANCE);
                        pipeline.addLast(LoginRequestHandler.INSTANCE);
                        pipeline.addLast(MessageRequestHandler.INSTANCE);
                        pipeline.addLast(CheckOnlineStateRequestHandler.INSTANCE);
                    }
                });
        try {
            bind(b);
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    private void bind(ServerBootstrap b) throws InterruptedException {
        Channel channel = b.bind(PORT).addListener(new GenericFutureListener<Future<? super Void>>() {
            @Override
            public void operationComplete(Future<? super Void> future) throws Exception {
                if (future.isSuccess()) {
                    log.info("Tcp服务器启动成功,绑定端口[{}]", PORT);
                    bindListener.success(new Operator());
                } else {
                    log.error("Tcp服务器启动失败");
                    bindListener.fail("启动失败");
                }
            }
        }).channel();
        channel.closeFuture().sync();
    }
}
