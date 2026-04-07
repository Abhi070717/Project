package in.co.rays.proj4.bean;

public class PhotographerBean extends BaseBean {

	private String photographerName;
	private String eventType;
	private long charges;

	public String getPhotographerName() {
		return photographerName;
	}

	public void setPhotographerName(String photographerName) {
		this.photographerName = photographerName;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public long getCharges() {
		return charges;
	}

	public void setCharges(long charges) {
		this.charges = charges;
	}

	@Override
	public String getKey() {
		return photographerName;
	}

	@Override
	public String getValue() {
		return photographerName;
	}
}