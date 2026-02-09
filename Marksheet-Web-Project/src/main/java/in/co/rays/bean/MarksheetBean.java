package in.co.rays.bean;

import java.util.Date;

public class MarksheetBean {
	private int id;
	private String Name;
	private String UserName;
	private Date dob;
	private int RollNo;
	private int Physics;
	private int Chemistry;
	private int Maths;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public int getRollNo() {
		return RollNo;
	}

	public void setRollNo(int rollNo) {
		RollNo = rollNo;
	}

	public int getPhysics() {
		return Physics;
	}

	public void setPhysics(int physics) {
		Physics = physics;
	}

	public int getChemistry() {
		return Chemistry;
	}

	public void setChemistry(int chemistry) {
		Chemistry = chemistry;
	}

	public int getMaths() {
		return Maths;
	}

	public void setMaths(int maths) {
		Maths = maths;
	}
}
