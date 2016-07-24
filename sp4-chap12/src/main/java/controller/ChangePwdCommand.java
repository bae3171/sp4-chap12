package controller;

/**
 * 비밀번호변경 커맨드
 * 
 * @author assu
 * @date 2016. 7. 24.
 */
public class ChangePwdCommand {
	
	private String currentPassword;
	private String newPassword;
	
	public String getCurrentPassword() {
		return currentPassword;
	}
	public void setCurrentPassword(String currentPassword) {
		this.currentPassword = currentPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
}
