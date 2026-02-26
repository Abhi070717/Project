package in.co.rays.proj4.bean;

import java.util.Date;

public class NotificationBean extends BaseBean {

	private String code;
	private String message;
	private String sendto;
	private Date senttime;
	private String status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSendto() {
		return sendto;
	}

	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	public Date getSenttime() {
		return senttime;
	}

	public void setSenttime(Date senttime) {
		this.senttime = senttime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
