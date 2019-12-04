package together.togethercore.amqp;

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


/**
 * spring-amqp中RabbitMQ配置类，需要加入spring容器中
 */
@Configuration
@Component
public class AMQPNet{

    private  static AmqpAdmin amqpAdmin;

    @Autowired
    public  AMQPNet(AmqpAdmin amqpadmin) {
        amqpAdmin = amqpadmin;
    }

    /**
     * 配置RabbitMQ序列化方式
     * 需要使用@Configuration
     * @return
     */
    @Bean
    public MessageConverter messageConverter(){
          return new Jackson2JsonMessageConverter();
    }
     

    /**
     * AMQP配置方法
     * @param Queuename 队列名称
     */
    public static void AMQPConfiguration(String Queuename){
        amqpAdmin.declareExchange(new DirectExchange(Queuename));
        amqpAdmin.declareQueue(new Queue(Queuename, true));
        amqpAdmin.declareBinding(new Binding(Queuename, DestinationType.QUEUE, Queuename, Queuename, null));
    }

}