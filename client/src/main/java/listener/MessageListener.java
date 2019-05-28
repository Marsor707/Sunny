package listener;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public interface MessageListener {

    void onSingleTextMessageReceived(String fromPushId, String msg);

    void onSystemTextMessageReceived(String msg);
}
