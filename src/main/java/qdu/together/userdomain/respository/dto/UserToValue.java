package qdu.together.userdomain.respository.dto;

import qdu.together.togethercore.respository.DataTransfer;
import qdu.together.userdomain.dao.User;
import qdu.together.userdomain.entity.UserValue;

public  class  UserToValue implements DataTransfer<User> {

	@Override
	public Object changeData(User Value) {
        UserValue userValue=new UserValue();
        userValue.userAge=Value.getUserAge();
        userValue.userBackgroud=Value.getUserBackgroud();
        userValue.userBirthday=Value.getUserBirthday();
        userValue.userGender=Value.getUserGender();
        userValue.userID=Value.getUserID();
        userValue.userJob=Value.getUserJob();
        userValue.userName=Value.getUserName();
        userValue.userWechat=Value.getUserWechat();
        userValue.userWorkplace=Value.getUserWorkplace();
		return userValue;
	}
}