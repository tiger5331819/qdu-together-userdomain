package qdu.together.userdomain.netservice;

import qdu.together.net.Message;
import qdu.together.togethercore.respository.RespositoryAccess;
import qdu.together.togethercore.service.DomainService;
import qdu.together.togethercore.service.ServiceDo;
import qdu.together.userdomain.aop.myannotation;
import qdu.together.userdomain.core.UserDomainCore;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.UserRespository;

/**
 * 领域对外响应请求服务需要加上注解@ServiceDo
 * 并且加上注解@DomainService（ServiceName=“具体服务名称”）
 * 核心将根据服务名称进行Route
 * 
 * 现已经支持自定义函数注解
 */
@DomainService(ServiceName = "ServiceExample")
public class ServiceExample{
    
    /**
     * 此注解为框架注解，通过该注解，核心将运行@DomainService 注解标注类的方法。
     * @param message 
     */
    @ServiceDo
    public void doService(Message message) {
        System.out.println("ServiceExample");   
        UserDomainCore core=UserDomainCore.getInstance();  
        RespositoryAccess res= (UserRespository) core.getRespository("UserRespository");//通过核心得到对应存储库
        
        UserEntity entity= (UserEntity) res.getEntity(message.Data);//从存储库中得到实体
        message.Data=entity.getValue();//从实体中得到值对象
        message.isSuccessBoolean="true";//描述状态
        core.setsendMessage(message);//发送结果
    }
   
    /**
     * 此注解为自定义注解，通过自定义注入框架的AOP来对特殊请求进行响应。
     * @param message
     */
    @myannotation
    public void dome(Message message){
        System.out.println("dome");   
        UserDomainCore core=UserDomainCore.getInstance();  
        RespositoryAccess res= (UserRespository) core.getRespository("UserRespository");//通过核心得到对应存储库
        
        UserEntity entity= (UserEntity) res.getEntity(message.Data);//从存储库中得到实体
        message.Data=entity.getValue();//从实体中得到值对象
        message.isSuccessBoolean="true";//描述状态
        core.setsendMessage(message);//发送结果
    }
}