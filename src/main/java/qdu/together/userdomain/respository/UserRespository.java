package qdu.together.userdomain.respository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import qdu.mapping.UserMapper;
import qdu.together.togethercore.respository.Respository;
import qdu.together.togethercore.respository.RespositoryAccess;
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
        return get((String)obj);
    }

    @Override
    public void updateEntity(Object obj) {
        UserEntity entity=(UserEntity)obj;    
        changeEntity(getEntityIdentity(entity), entity);
    }

    @Override
    public void removeEntity(Object obj) {
        removeEntity((String)obj);
    }

    @Override
    public void putEntity(Object obj) {
        addEntityToAddQueue((UserEntity)obj);
    }

    @Override
    public void saveEntity(UserEntity v) {
        ValueToUser dto=new ValueToUser();        
        mapper.update((User) dto.changeData(v.getValue()));
        System.out.println("Save success!");
    }

    @Override
    public void deleteEntity(Object obj) {
        delete((String)obj);
        mapper.delete((String)obj);

    }

}