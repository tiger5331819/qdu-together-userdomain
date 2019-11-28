package qdu.together.net.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import qdu.together.net.Message;
import qdu.together.userdomain.core.UserDomainCore;

@Component
public class MQListener{

    @RabbitListener(queues = "UserDomain")
    public void getMessage(Message message){
        UserDomainCore core=UserDomainCore.getInstance();
        core.setNewreceiveMessage(message);
    }

}