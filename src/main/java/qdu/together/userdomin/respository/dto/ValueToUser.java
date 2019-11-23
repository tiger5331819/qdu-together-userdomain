package qdu.together.userdomin.respository.dto;

import qdu.together.userdomin.core.DataTransfer;
import qdu.together.userdomin.dao.User;
import qdu.together.userdomin.entity.UserValue;

public class ValueToUser implements DataTransfer<UserValue>{

    @Override
    public Object changeData(UserValue Value) {
       User user =new User(Value.userID,Value.userName,
                           Value.userGender,Value.userWechat,
                           Value.userBirthday,Value.userAge,
                           Value.userJob,Value.userWorkplace,
                           Value.userBackgroud);
       
        return user;
    }
    
}