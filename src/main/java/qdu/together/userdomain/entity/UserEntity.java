package qdu.together.userdomain.entity;

import qdu.together.togethercore.DataTransfer;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.respository.dto.UserToValue;

public class UserEntity {
    UserValue value;

    public UserEntity(User user){
        DataTransfer<User> transfer=  new UserToValue();
        value=(UserValue)transfer.changeData(user);
    }


    public UserValue getValue(){
        return value;
    }
    public void changeValue(UserValue value){
        this.value=value;
    }


    @Override
    public String toString() {
        return value.toString();
    }
   
}