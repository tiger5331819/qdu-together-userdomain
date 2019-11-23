package qdu.together.userdomin.entity;

import qdu.together.userdomin.core.DataTransfer;
import qdu.together.userdomin.dao.User;
import qdu.together.userdomin.respository.dto.UserToValue;

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