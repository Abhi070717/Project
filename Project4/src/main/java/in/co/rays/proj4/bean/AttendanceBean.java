package in.co.rays.proj4.bean;

import java.util.Date;

public class AttendanceBean extends BaseBean {

	private String attendanceCode;
	private String employeeName;
	private Date date;
	private String status;

	public String getAttendanceCode() {
		return attendanceCode;
	}

	public void setAttendanceCode(String attendanceCode) {
		this.attendanceCode = attendanceCode;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return attendanceCode;
	}

	@Override
	public String getValue() {
		return attendanceCode;
	}

}
