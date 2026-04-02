package in.co.rays.proj4.bean;

import java.util.Date;

public class PressBean extends BaseBean {

	private String title;
	private Date releaseDate;
	private String author;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String getKey() {
		return title;
	}

	@Override
	public String getValue() {
		return title;
	}

}
