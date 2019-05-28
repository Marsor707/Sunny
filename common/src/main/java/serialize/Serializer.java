package serialize;


import serialize.impl.JSONSerializer;

/**
 * Author: Marsor
 * Github: https://github.com/Marsor707
 * Email: 369135912@qq.com
 */
public interface Serializer {

    Serializer DEFAULT = new JSONSerializer();

    byte getSerializeAlgorithm();

    /**
     * 序列化
     */
    byte[] serialize(Object object);

    /**
     * 反序列化
     */
    <T> T deSerialize(Class<T> clz, byte[] bytes);
}
