package in.co.rays.proj4.bean;

public class BloodBankBean extends BaseBean {

	private String bloodGroup;
	private Integer unitsAvailable;
	private String location;

	public String getbloodGroup() {
		return bloodGroup;
	}

	public void setbloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public Integer getunitsAvailable() {
		return unitsAvailable;
	}

	public void setunitsAvailable(Integer unitsAvailable) {
		this.unitsAvailable = unitsAvailable;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String getKey() {
		return bloodGroup;
	}

	@Override
	public String getValue() {
		return bloodGroup;
	}
}