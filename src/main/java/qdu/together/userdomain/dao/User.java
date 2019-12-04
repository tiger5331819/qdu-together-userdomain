package qdu.together.userdomain.dao;

import java.sql.Date;
import java.util.Arrays;

/**
 * DAO
 */
public class User {
	private String userID;
	private String userName;
	private String userGender;
	private String userWechat;
	private Date userBirthday;
	private int userAge;
	private String userJob;
	private String userWorkplace;
	private byte[] userBackgroud;
	private byte[] userTouxiang;

	public User(String user_ID, String user_Name, String user_Gender, String user_Wechat, Date user_Birthday,
			int user_Age, String user_Job, String user_Workplace, byte[] user_Backgroud, byte[] user_Touxiang) {
		this.userID = user_ID;
		this.userName = user_Name;
		this.userGender = user_Gender;
		this.userWechat = user_Wechat;
		this.userBirthday = user_Birthday;
		this.userAge = user_Age;
		this.userJob = user_Job;
		this.userWorkplace = user_Workplace;
		this.userBackgroud = user_Backgroud;
		this.userTouxiang = user_Touxiang;

	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserGender() {
		return userGender;
	}

	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}

	public String getUserWechat() {
		return userWechat;
	}

	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}

	public Date getUserBirthday() {
		return userBirthday;
	}

	public void setUserBirthday(Date userBirthday) {
		this.userBirthday = userBirthday;
	}

	public int getUserAge() {
		return userAge;
	}

	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}

	public String getUserJob() {
		return userJob;
	}

	public void setUserJob(String userJob) {
		this.userJob = userJob;
	}

	public String getUserWorkplace() {
		return userWorkplace;
	}

	public void setUserWorkplace(String userWorkplace) {
		this.userWorkplace = userWorkplace;
	}

	public byte[] getUserBackgroud() {
		return userBackgroud;
	}

	public void setUserBackgroud(byte[] userBackgroud) {
		this.userBackgroud = userBackgroud;
	}

	public byte[] getUserTouxiang() {
		return userTouxiang;
	}

	public void setUserTouxiang(byte[] userTouxiang) {
		this.userTouxiang = userTouxiang;
	}

	@Override
	public String toString() {
		return "User [userAge=" + userAge + ", userBackgroud=" + Arrays.toString(userBackgroud) + ", userBirthday="
				+ userBirthday + ", userGender=" + userGender + ", userID=" + userID + ", userJob=" + userJob
				+ ", userName=" + userName + ", userTouxiang=" + Arrays.toString(userTouxiang) + ", userWechat="
				+ userWechat + ", userWorkplace=" + userWorkplace + "]";
	}

	
}
