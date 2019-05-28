package ws.listener;

import ws.operator.Operator;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public interface BindListener {

    void success(Operator operator);

    void fail(String reason);
}
