package together.example.demo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class TestListener{

    @RabbitListener(queues = "Test")
    public void getMessage(Message message){
        System.out.println(message);
        TestResult.queue.add(message);
    }

}