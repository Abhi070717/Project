package in.co.rays.proj4.bean;

public class EmployeeBean extends BaseBean {
	private String name;
	private String department;
	private double salary;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public double getSalary() {
		return salary;
	}

	public void setSalary(double salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return name;
	}

	@Override
	public String getValue() {
		return name;
	}
}
