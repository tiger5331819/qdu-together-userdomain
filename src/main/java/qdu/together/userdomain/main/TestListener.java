package qdu.together.userdomain.main;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import qdu.together.net.Message;

@Component
public class TestListener{

    @RabbitListener(queues = "Test")
    public void getMessage(Message message){
        TestResult.queue.add(message.Data);
    }

}