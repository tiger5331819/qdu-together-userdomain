package qdu.together.userdomin.respository;

import java.util.concurrent.ConcurrentHashMap;

import qdu.mapping.UserMapper;
import qdu.together.togethercore.Respository;
import qdu.together.togethercore.RespositoryAccess;
import qdu.together.userdomin.core.UserDomainCore;
import qdu.together.userdomin.dao.User;
import qdu.together.userdomin.entity.UserEntity;
import qdu.together.userdomin.respository.dto.ValueToUser;

public class UserRespository extends Respository<Integer,UserEntity> 
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
              new ConcurrentHashMap<>(),1);    
    }
    public void UserRespositoryConfiguration(){
            UserDomainCore core=UserDomainCore.getInstance();
            mapper= (UserMapper) core.Context.getBean("userMapper");
            User user=mapper.findbyid(1002);
            UserEntity entity=new UserEntity(user);

            AddEntityToAddQueue(entity);

            user=mapper.findbyid(1047);
            entity=new UserEntity(user);
            AddEntityToAddQueue(entity);
            System.out.println("UserRespository is ready!");
    }  
     

    @Override
    public Integer GetEntityIdentity(UserEntity v) {
        return v.getValue().userID;
    }

    @Override
    public Object get(Object obj) {
        return getEntity((Integer)obj);
    }

    @Override
    public void updateEntity(Object obj) {
        UserEntity entity=(UserEntity)obj;    
        ChangeEntity(GetEntityIdentity(entity), entity);
    }

    @Override
    public void removeEntity(Object obj) {
        RemoveEntity((Integer)obj);
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
        DeleteEntity((Integer)obj);
        mapper.delete((Integer)obj);

    }

}