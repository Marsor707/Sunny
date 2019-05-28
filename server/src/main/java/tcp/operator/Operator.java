package tcp.operator;

import protocol.request.SystemMessageRequestPacket;
import tcp.utils.SessionUtil;
import ws.container.WsContainer;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class Operator {

    public void sendSystemMessage(String message) {
        SessionUtil.getChannelGroup().writeAndFlush(new SystemMessageRequestPacket(message));
    }

    public Integer getOnlineUserCount() {
        return SessionUtil.getChannelGroup().size();
    }

    public void sendToWebsocket(String sid, String message) {
        WsContainer.getInstance().sendToSid(sid, message);
    }
}
