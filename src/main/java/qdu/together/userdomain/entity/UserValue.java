package qdu.together.userdomain.entity;

import java.sql.Date;

public class UserValue {
	public String userID;
	public String userName;
	public String userGender;
	public String userWechat;
	public Date userBirthday;
	public int userAge;
	public String userJob;
	public String userWorkplace;
	public byte[] userBackgroud;
	public byte[] userTouxiang;

	@Override
	public String toString() {
		return "UserValue [userAge=" + userAge + ", userBirthday="
				+ userBirthday + ", userGender=" + userGender + ", userID=" + userID + ", userJob=" + userJob
				+ ", userName=" + userName + ", userWechat=" + userWechat + ", userWorkplace=" + userWorkplace + "]";
	}
	
}