package ws.operator;

import ws.container.WsContainer;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public class Operator {

    public void sendToSid(String sid, String message) {
        WsContainer.getInstance().sendToSid(sid, message);
    }
}
