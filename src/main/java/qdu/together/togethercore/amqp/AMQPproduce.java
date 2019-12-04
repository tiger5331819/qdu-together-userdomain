package qdu.together.togethercore.amqp;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * spring-amqp消息发送
 */
public abstract class AMQPproduce{

    protected volatile static AmqpTemplate Template;

    @Autowired
    public AMQPproduce(AmqpTemplate template){
        Template=template;
    } 
}