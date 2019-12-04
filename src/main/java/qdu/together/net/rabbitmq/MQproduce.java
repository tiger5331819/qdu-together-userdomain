package qdu.together.net.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import qdu.together.net.Message;
import qdu.together.togethercore.amqp.AMQPproduce;

/**
 * spring-amqp消息发送类，需要加入spring容器中
 * 继承自AMQPproduce
 * 自定义消息发送方式，使用Template.convertAndSend方法将数据进行序列化并发送指定队列中
 */
@Component
public class MQproduce extends AMQPproduce{
    
    public MQproduce(AmqpTemplate template) {
        super(template);
    }

    /**
     * 自定义消息发送方法
     * @param message
     * @return 如果发送成功为true，否则为false
     */
    public static Boolean sendMessage(Message message) {
        try{
            Template.convertAndSend(message.DestinationQueueName, message);
        }catch(AmqpException ex){
            System.out.println(ex);
            return false;
        }
        return true;
    }

}