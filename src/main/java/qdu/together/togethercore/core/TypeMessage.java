package qdu.together.togethercore.core;

/**
 * 消息类型枚举
 * @author 苏琥元
 * @version 泰建雅0.4
 */
public enum TypeMessage{
    /**
     * RabbitMQ的消息
     */
    Message,

    /**
     * 事件
     */
    Event,

    /**
     * 对象
     */
    Object,

    /**
     * 不接收消息
     */
    NULL
}