package qdu.together.userdomain.respository;

import java.util.concurrent.ConcurrentHashMap;

import qdu.mapping.UserMapper;
import qdu.together.togethercore.Respository;
import qdu.together.togethercore.RespositoryAccess;
import qdu.together.userdomain.core.UserDomainCore;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.entity.UserEntity;
import qdu.together.userdomain.respository.dto.ValueToUser;

public class UserRespository extends Respository<String,UserEntity> 
                             implements RespositoryAccess{
    private UserMapper mapper;

    private volatile static UserRespository instance;
    public static UserRespository getInstance() { 
        if(instance==null){
            synchronized(UserRespository.class){
                if(instance==null) instance=new UserRespository();
            }
        } 
        return instance;  
    }  


    private UserRespository (){

        super(new ConcurrentHashMap<>(),
              new ConcurrentHashMap<>(),127,99999);    
    }
    public void UserRespositoryConfiguration(){
            UserDomainCore core=UserDomainCore.getInstance();
            mapper= (UserMapper) core.getContext().getBean("userMapper");
            User user=mapper.findbyid("1001");
            UserEntity entity=new UserEntity(user);

            AddEntityToAddQueue(entity);

            user=mapper.findbyid("1047");
            entity=new UserEntity(user);
            AddEntityToAddQueue(entity);
            System.out.println("UserRespository is ready!");
    }  
     

    @Override
    public String GetEntityIdentity(UserEntity v) {
        return v.getValue().userID;
    }

    @Override
    public Object get(Object obj) {
        return getEntity((String)obj);
    }

    @Override
    public void updateEntity(Object obj) {
        UserEntity entity=(UserEntity)obj;    
        ChangeEntity(GetEntityIdentity(entity), entity);
    }

    @Override
    public void removeEntity(Object obj) {
        RemoveEntity((String)obj);
    }

    @Override
    public void putEntity(Object obj) {
        AddEntityToAddQueue((UserEntity)obj);
    }

    @Override
    public void SaveEntity(UserEntity v) {
        ValueToUser dto=new ValueToUser();        
        mapper.update((User) dto.changeData(v.getValue()));
        System.out.println("Save success!");
    }

    @Override
    public void deleteEntity(Object obj) {
        DeleteEntity((String)obj);
        mapper.delete((String)obj);

    }

}