package qdu.together.userdomin.respository;

import java.util.Hashtable;

import qdu.mapping.UserMapper;
import qdu.together.userdomin.core.UserDomainCore;
import qdu.together.userdomin.dao.User;
import qdu.together.userdomin.entity.UserEntity;
import qdu.together.userdomin.respository.dto.ValueToUser;

public class UserRespository{
    private Hashtable<Integer,UserEntity> userEntity=new Hashtable<Integer,UserEntity>();
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
            UserDomainCore core=UserDomainCore.getInstance();
            mapper= (UserMapper) core.Context.getBean("userMapper");
            User user=mapper.findbyid(1002);
            UserEntity entity=new UserEntity(user);
            userEntity.put(user.getUserID(), entity);
            user=mapper.findbyid(1047);
            entity=new UserEntity(user);
            userEntity.put(user.getUserID(),entity);
    }  
     
    public Object getEntity(int id){
        return userEntity.get(id);
    }
    public void updateEntity(UserEntity entity){
        ValueToUser dto=new ValueToUser();      
        mapper.update((User) dto.changeData(entity.getValue()));
        userEntity.replace(entity.getValue().userID, entity);
    }

}