package qdu.together.userdomin.dao;

import java.sql.Date;

public class User {
	private int userID;
	private String userName;
	private String userGender;
	private String userWechat;
	private Date userBirthday;
	private int userAge;
	private String userJob;
	private String userWorkplace;
	private byte[] userBackgroud;

	@Override
	public String toString() {
		return "Test [userAge=" + userAge + ", userBackgroud=" + userBackgroud + ", userBirthday=" + userBirthday
				+ ", userGender=" + userGender + ", userID=" + userID + ", userJob=" + userJob + ", userName="
				+ userName + ", userWechat=" + userWechat + ", userWorkplace=" + userWorkplace + "]";
	}

	public User(int userID, String userName, String userGender, String userWechat, Date userBirthday, int userAge,
			String userJob, String userWorkplace, byte[] userBackgroud) {
		this.userID = userID;
		this.userName = userName;
		this.userGender = userGender;
		this.userWechat = userWechat;
		this.userBirthday = userBirthday;
		this.userAge = userAge;
		this.userJob = userJob;
		this.userWorkplace = userWorkplace;
		this.userBackgroud = userBackgroud;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
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

	
}
