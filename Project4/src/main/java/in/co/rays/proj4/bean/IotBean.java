package in.co.rays.proj4.bean;

public class IotBean extends BaseBean {
	private int deviceId;
	private String Strength;
	private String batteryLevel;
	private String lastPing;

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public String getStrength() {
		return Strength;
	}

	public void setStrength(String strength) {
		Strength = strength;
	}

	public String getBatteryLevel() {
		return batteryLevel;
	}

	public void setBatteryLevel(String batteryLevel) {
		this.batteryLevel = batteryLevel;
	}

	public String getLastPing() {
		return lastPing;
	}

	public void setLastPing(String lastPing) {
		this.lastPing = lastPing;
	}

	@Override
	public String getKey() {
		return Strength;
	}

	@Override
	public String getValue() {
		return Strength;
	}

}
