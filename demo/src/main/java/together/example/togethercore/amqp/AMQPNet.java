package together.example.togethercore.amqp;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Binding.DestinationType;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@Component
public class AMQPNet{

    private  static AmqpAdmin amqpAdmin;

    @Autowired
    public  AMQPNet(AmqpAdmin amqpadmin) {
        amqpAdmin = amqpadmin;
    }

    @Bean
    public MessageConverter messageConverter(){
          return new Jackson2JsonMessageConverter();
    }
     

    public static void AMQPConfiguration(String Queuename){
        amqpAdmin.declareExchange(new DirectExchange(Queuename));
        amqpAdmin.declareQueue(new Queue(Queuename, true));
        amqpAdmin.declareBinding(new Binding(Queuename, DestinationType.QUEUE, Queuename, Queuename, null));
    }

}