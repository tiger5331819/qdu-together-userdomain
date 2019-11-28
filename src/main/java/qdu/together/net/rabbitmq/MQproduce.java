package qdu.together.net.rabbitmq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import qdu.together.net.Message;


@Component
public class MQproduce {
    
    private  static AmqpTemplate Template;
    
    @Autowired
    public MQproduce(AmqpTemplate template){
        Template=template;
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