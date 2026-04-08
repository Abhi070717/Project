package in.co.rays.proj4.bean;

public class ThemeBean extends BaseBean {
	private String themeCode;
	private String themeName;
	private String color;
	private String status;

	public String getThemeCode() {
		return themeCode;
	}

	public void setThemeCode(String themeCode) {
		this.themeCode = themeCode;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String getKey() {
		return themeName;
	}

	@Override
	public String getValue() {
		return themeName;
	}

}
