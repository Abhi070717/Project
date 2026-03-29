package in.co.rays.proj4.bean;

import java.util.Date;

public class MaintenanceBean extends BaseBean {

	private String requestName;
	private String issueType;
	private String location;
	private Date requestDate;

	public String getRequestName() {
		return requestName;
	}

	public void setRequestName(String requestName) {
		this.requestName = requestName;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	@Override
	public String getKey() {
		return requestName;
	}

	@Override
	public String getValue() {
		return requestName;
	}

}
