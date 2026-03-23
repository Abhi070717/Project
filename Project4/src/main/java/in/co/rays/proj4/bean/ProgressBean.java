package in.co.rays.proj4.bean;

import java.util.Date;

public class ProgressBean extends BaseBean {

	private String DeveloperName;
	private String Work;
	private String Target;
	private String LastWeek;
	private String CurrentWeek;
	private Date Today;

	public String getDeveloperName() {
		return DeveloperName;
	}

	public void setDeveloperName(String developerName) {
		DeveloperName = developerName;
	}

	public String getWork() {
		return Work;
	}

	public void setWork(String work) {
		Work = work;
	}

	public String getTarget() {
		return Target;
	}

	public void setTarget(String target) {
		Target = target;
	}

	public String getLastWeek() {
		return LastWeek;
	}

	public void setLastWeek(String lastWeek) {
		LastWeek = lastWeek;
	}

	public String getCurrentWeek() {
		return CurrentWeek;
	}

	public void setCurrentWeek(String currentWeek) {
		CurrentWeek = currentWeek;
	}

	public Date getToday() {
		return Today;
	}

	public void setToday(Date today) {
		Today = today;
	}

	@Override
	public String getKey() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getValue() {
		// TODO Auto-generated method stub
		return null;
	}

}
