package qdu.together.userdomain.main;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import qdu.together.net.Message;
import qdu.together.net.rabbitmq.MQproduce;
import qdu.together.userdomain.entity.UserValue;

@RestController
public class Test {

    @RequestMapping("/1")
    public Object index() throws InterruptedException {

        Message message = new Message();
        message.Data = "1001";
        message.isSuccessBoolean = "";
        message.LocalQueueName = "Test";
        message.DestinationQueueName = "UserDomain";
        message.ServiceRequest = "serviceExample";
        MQproduce.sendMessage(message);
        return TestResult.queue.take();
    }
    /* @RequestMapping("/1")
    public UserValue index() {
        UserRespository respository = UserRespository.getInstance();
        try {
            
            Thread.sleep(1000);
            UserEntity entity= (UserEntity) respository.get("1047");
            return  entity.getValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
        
    } */
    
}