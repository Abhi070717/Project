	package in.co.rays.proj4.bean;

public class WareHouseBean extends BaseBean {

	private String staffName;
	private String role;
	private Double salary;

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getSalary() {
		return salary;
	}

	public void setSalary(Double salary) {
		this.salary = salary;
	}

	@Override
	public String getKey() {
		return role;
	}

	@Override
	public String getValue() {
		return role;
	}

}
