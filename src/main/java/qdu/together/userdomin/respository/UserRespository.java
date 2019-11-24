package qdu.together.userdomin.respository;

import java.util.concurrent.ConcurrentHashMap;

import qdu.mapping.UserMapper;
import qdu.together.togethercore.Respository;
import qdu.together.userdomin.core.UserDomainCore;
import qdu.together.userdomin.dao.User;
import qdu.together.userdomin.entity.UserEntity;
import qdu.together.userdomin.respository.dto.ValueToUser;

public class UserRespository extends Respository<Integer,UserEntity>{
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

        super(new ConcurrentHashMap<>(127),
              new ConcurrentHashMap<>(127));


            
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
     
    public Object get(int id){
        return getEntity(id);
    }
    public void updateEntity(UserEntity entity){
        ValueToUser dto=new ValueToUser();      
        mapper.update((User) dto.changeData(entity.getValue()));
        ChangeEntity(GetEntityIdentity(entity), entity);
    }

    @Override
    public Integer GetEntityIdentity(UserEntity v) {
        return v.getValue().userID;
    }

}