package qdu.together.net.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import qdu.together.net.Message;
import qdu.together.userdomain.core.UserDomainCore;

/**
 * Spring-amqp中对于RabbitMQ通道监听对象，需要加入spring容器中
 * @RabbitListener注解用于监听具体队列名
 * 函数为接受到消息后的具体处理
 */
@Component
public class MQListener{

    @RabbitListener(queues = "UserDomain")
    public void getMessage(Message message){
        UserDomainCore core=UserDomainCore.getInstance();
        core.setNewreceiveMessage(message);//将消息移交至核心进行route
    }

}