package in.co.rays.proj4.bean;

import java.util.Date;

public class SessionBean extends BaseBean {
	private String sessionCode;
	private String userName;
	private Date loginTime;
	private String status;

	public String getSessionCode() {
		return sessionCode;
	}

	public void setSessionCode(String sessionCode) {
		this.sessionCode = sessionCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return userName;
	}

	@Override
	public String getValue() {
		return userName;
	}

}
