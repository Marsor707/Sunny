package ws.listener;

import ws.operator.Pusher;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public interface ConnectionListener {

    void success(Pusher pusher);

    void fail(String reason);
}
