package qdu.together.userdomain.netservice;

import qdu.together.net.Message;
import qdu.together.togethercore.respository.RespositoryAccess;
import qdu.together.togethercore.service.DomainService;
import qdu.together.userdomain.core.UserNetService;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.UserRespository;

@DomainService(ServiceName = "ServiceExample")
public class ServiceExample implements UserNetService {

    @Override
    public void doService(Message message) {
        System.out.println("ServiceExample");     
        RespositoryAccess res= (UserRespository) core.getRespository("UserRespository");
        UserEntity entity= (UserEntity) res.getEntity(message.Data);
        message.Data=entity.getValue();
        message.isSuccessBoolean="true";
        core.setsendMessage(message);
    }
    
}