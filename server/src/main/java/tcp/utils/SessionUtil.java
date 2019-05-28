package tcp.utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tcp.attribute.Attributes;
import tcp.session.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class SessionUtil {

    private static final Logger log = LoggerFactory.getLogger(SessionUtil.class);

    //保存已登陆的pushId-channel
    private static final Map<String, Channel> userChannelMap = new ConcurrentHashMap<>();
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void bindSession(Session session, Channel channel) {
        if (userChannelMap.containsKey(session.getPushId())) {
            throw new RuntimeException("用户已登陆，请勿重复登陆");
        }
        userChannelMap.put(session.getPushId(), channel);
        channelGroup.add(channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            Session session = getSession(channel);
            userChannelMap.remove(session.getPushId());
            channelGroup.remove(channel);
            channel.attr(Attributes.SESSION).set(null);
            log.info("退出成功，pushId：{}", session.getPushId());
        }
    }

    public static boolean hasLogin(Channel channel) {
        return getSession(channel) != null;
    }

    public static boolean hasLogin(String pushId) {
        return getChannel(pushId) != null;
    }

    public static Session getSession(Channel channel) {
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String pushId) {
        return userChannelMap.get(pushId);
    }

    public static ChannelGroup getChannelGroup() {
        return channelGroup;
    }

}
