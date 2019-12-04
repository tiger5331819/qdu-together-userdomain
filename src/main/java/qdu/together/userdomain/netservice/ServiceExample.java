package qdu.together.userdomain.netservice;

import qdu.together.net.Message;
import qdu.together.togethercore.respository.RespositoryAccess;
import qdu.together.togethercore.service.DomainService;
import qdu.together.userdomain.core.UserNetService;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.UserRespository;

/**
 * 领域对外响应请求服务需要继承接口UserNetService或者NetService
 * 并且加上注解@DomainService（ServiceName=“具体服务名称”）
 * 核心将根据服务名称进行Route
 */
@DomainService(ServiceName = "ServiceExample")
public class ServiceExample implements UserNetService {

    @Override
    public void doService(Message message) {
        System.out.println("ServiceExample");     
        RespositoryAccess res= (UserRespository) core.getRespository("UserRespository");//通过核心得到对应存储库
        
        UserEntity entity= (UserEntity) res.getEntity(message.Data);//从存储库中得到实体
        message.Data=entity.getValue();//从实体中得到值对象
        message.isSuccessBoolean="true";//描述状态
        core.setsendMessage(message);//发送结果
    }
    
}