package in.co.rays.proj4.bean;

import java.util.Date;

public class BroadcastBean extends BaseBean {

	private String broadcastCode;
	private String messageTitle;
	private String messageContent;
	private Date broadcastTime;
	private String broadcastStatus;

	public String getBroadcastCode() {
		return broadcastCode;
	}

	public void setBroadcastCode(String broadcastCode) {
		this.broadcastCode = broadcastCode;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public Date getBroadcastTime() {
		return broadcastTime;
	}

	public void setBroadcastTime(Date broadcastTime) {
		this.broadcastTime = broadcastTime;
	}

	public String getBroadcastStatus() {
		return broadcastStatus;
	}

	public void setBroadcastStatus(String broadcastStatus) {
		this.broadcastStatus = broadcastStatus;
	}

	@Override
	public String getKey() {
		return null;
	}

	@Override
	public String getValue() {
		return null;
	}

}
