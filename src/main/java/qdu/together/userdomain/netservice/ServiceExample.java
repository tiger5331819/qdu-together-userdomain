package qdu.together.userdomain.netservice;

import org.springframework.stereotype.Component;

import qdu.together.net.Message;
import qdu.together.togethercore.NetService;
import qdu.together.userdomain.core.UserDomainCore;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.UserRespository;

@Component
public class ServiceExample implements NetService {

    @Override
    public void doService(Message message) {
        System.out.println("ServiceExample");
        UserRespository res=UserRespository.getInstance();
        UserEntity entity= (UserEntity) res.get("1001");
        Message mes=new Message();
        mes.Data=entity.getValue();
        mes.LocalQueueName=message.DestinationQueueName;
        mes.DestinationQueueName=message.LocalQueueName;
        mes.ServiceRequest=message.ServiceRequest;
        mes.isSuccessBoolean="true";
        UserDomainCore core=UserDomainCore.getInstance();
        core.setsendMessage(mes);
    }
    
}