package together.example.demo;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import together.togethercore.amqp.AMQPproduce;

@Component
public class MQproduce extends AMQPproduce {

    public MQproduce(AmqpTemplate template) {
        super(template);
    }
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