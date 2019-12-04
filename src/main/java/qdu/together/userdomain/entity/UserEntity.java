package qdu.together.userdomain.entity;

import qdu.together.togethercore.respository.DataTransfer;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.respository.dto.UserToValue;

public class UserEntity {
    private UserValue value;

    public UserEntity(User user){
        DataTransfer<User> transfer=  new UserToValue();
        value=(UserValue)transfer.changeData(user);
    }


    /**
     * 获得值对象
     * @return UserValue
     */
    public UserValue getValue(){
        return value;
    }

    /**
     * 更改实体内部的值对象（特指UserValue）
     * @param value UserValue值对象
     */
    public void changeValue(UserValue value){
        this.value=value;
    }


    @Override
    public String toString() {
        return value.toString();
    }
   
}