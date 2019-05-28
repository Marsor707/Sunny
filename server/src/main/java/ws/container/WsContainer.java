package ws.container;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class WsContainer {

    private static final WsContainer INSTANCE = new WsContainer();
    private AttributeKey<String> SID = AttributeKey.newInstance("sid");
    private Map<String, ChannelGroup> groupContainer = new ConcurrentHashMap<>();
    private final Logger logger = LoggerFactory.getLogger(WsContainer.class);

    private WsContainer() {
    }

    public static WsContainer getInstance() {
        return INSTANCE;
    }

    public void add(String sid, Channel channel) {
        if (!groupContainer.containsKey(sid)) {
            groupContainer.put(sid, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        }
        groupContainer.get(sid).add(channel);
        channel.attr(SID).set(sid);
    }

    public void remove(Channel channel) {
        String sid = channel.attr(SID).get();
        if (sid == null || sid.length() == 0 || !groupContainer.containsKey(sid)) {
            channel.close();
        }
        ChannelGroup channelGroup = groupContainer.get(sid);
        if (channelGroup != null) {
            channelGroup.remove(channel);
        }
    }

    private ChannelGroup getChannelGroupBySid(String sid) {
        if (groupContainer.containsKey(sid)) {
            return groupContainer.get(sid);
        }
        return null;
    }

    public ChannelGroup getChannelGroupByChannel(Channel channel) {
        String sid = channel.attr(SID).get();
        return getChannelGroupBySid(sid);
    }

    public void sendToSid(String sid, String message) {
        ChannelGroup channelGroup = getChannelGroupBySid(sid);
        if (channelGroup != null) {
            channelGroup.writeAndFlush(new TextWebSocketFrame(message));
        } else {
            logger.warn("sid窗口无连接");
        }
    }
}
