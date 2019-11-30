package qdu.together.userdomain.respository.dto;

import qdu.together.togethercore.respository.DataTransfer;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.entity.UserValue;

public class ValueToUser implements DataTransfer<UserValue>{

    @Override
    public Object changeData(UserValue Value) {
       User user =new User(Value.userID,Value.userName,
                           Value.userGender,Value.userWechat,
                           Value.userBirthday,Value.userAge,
                           Value.userJob,Value.userWorkplace,
                           Value.userBackgroud,Value.userTouxiang);
       
        return user;
    }
    
}