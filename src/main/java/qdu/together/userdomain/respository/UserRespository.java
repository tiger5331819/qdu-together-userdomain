package qdu.together.userdomain.respository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import qdu.mapping.UserMapper;
import qdu.together.togethercore.respository.DomainRespository;
import qdu.together.togethercore.respository.Respository;
import qdu.together.togethercore.respository.RespositoryAccess;
import qdu.together.userdomain.core.UserDomainCore;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.dto.ValueToUser;

@DomainRespository(RespositoryName = "UserRespository")
public class UserRespository extends Respository<String,UserEntity> 
                             implements RespositoryAccess{

                            
    /**
     * 链接数据库的Mybatis映射
     */
    private UserMapper mapper;

    /**
     * 单例模式
     */
    private volatile static UserRespository instance;
    public static UserRespository getInstance() { 
        if(instance==null){
            synchronized(UserRespository.class){
                if(instance==null) instance=new UserRespository();
            }
        } 
        return instance;  
    }  

    /**
     * 存储库配置
     */
    private UserRespository (){

        super(new ConcurrentHashMap<>(),
              new ConcurrentHashMap<>(),127,500);    
    }
    
    /**
     * 存储库初始化
     */
    @Override
    public void Configuration(){
            UserDomainCore core=UserDomainCore.getInstance();
            this.mapper= (UserMapper) core.getContext().getBean("userMapper");

            List<User>uList=getAllUser();
            for(User user :uList){
                putEntity(new UserEntity(user));
            }
            System.out.println("UserRespository is ready!");
    }  

    private List<User>getAllUser(){
        return mapper.findAll();
    }
     

    @Override
    public String getEntityIdentity(UserEntity v) {
        return v.getValue().userID;
    }

    @Override
    public Object getEntity(Object obj) {
        UserEntity entity=get((String)obj);
        if(entity==null){
            User newuser=mapper.findbyid((String)obj);
            if(newuser==null)return null;
            else{
                entity=new UserEntity(newuser);
                addEntityToAddQueue(entity);
            }
        }
        return entity;
    }

    @Override
    public Boolean updateEntity(Object obj) {
        UserEntity entity=(UserEntity)obj;
        if(saveEntity(entity)){
            if(changeEntity(getEntityIdentity(entity), entity)){
                return true;
            }else return false;            
        }else return false;
    }

    @Override
    public Boolean removeEntity(Object obj) {
        if(remove((String)obj)){
            return true;
        }else return false;
        
    }

    @Override
    public Boolean putEntity(Object obj) {
        if(addEntityToAddQueue((UserEntity)obj)){
            return true;
        }else return false;
    }

    @Override
    protected Boolean saveEntity(UserEntity v) {
        ValueToUser dto=new ValueToUser();        
        if(mapper.update((User) dto.changeData(v.getValue()))!=0){
            System.out.println("Save success!");
            return true;
        }else return false;
    }
    
    @Override
    public Boolean deleteEntity(Object obj) {
        if(delete((String)obj)){
            if(mapper.delete((String)obj)!=0){
                return true;
            }else return false;
        }else return false;
       
    }

}