package together.example.demo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import together.togethercore.amqp.AMQPCore;

@Component
public class TestListener{

    @RabbitListener(queues = "Test")
    public void getMessage(Message message){
        AMQPCore.getInstance().setMessage(message);
    }

}