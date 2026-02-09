package in.co.rays.MarksheetModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.bean.MarksheetBean;

public class MarksheetModel {

	// nextPk Query in student_Marksheet
	public int nextPk() throws Exception {

		int pk = 0;

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from student_marksheet");

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return pk + 1;
	}

	// Add Query in student_Marksheet
	public void add(MarksheetBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		System.out.println("Java is connected with MYSQL Successfully");

		PreparedStatement pstmt = conn.prepareStatement("insert into Student_Marksheet values(?, ?, ?, ?, ?, ?, ?, ?)");

		pstmt.setInt(1, nextPk());
		pstmt.setString(2, bean.getName());
		pstmt.setString(3, bean.getUserName());
		pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setInt(5, bean.getRollNo());
		pstmt.setInt(6, bean.getPhysics());
		pstmt.setInt(7, bean.getChemistry());
		pstmt.setInt(8, bean.getMaths());

		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Added  Duplicates: 0  Warnings: 0" + "\n" + "Data Inserted");

		pstmt.close();
		conn.close();
	}

	// Update Query in Student_Marksheet
	public void Update(MarksheetBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		System.out.println("Java is connected with MYSQL Successfully");

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_user set Name = ?, UserName = ?, dob = ? RollNo = ?, Physics = ?, Chemistry = ?, Maths = ? where id = ?");

		pstmt.setInt(8, bean.getId());
		pstmt.setString(1, bean.getName());
		pstmt.setString(2, bean.getUserName());
		pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setInt(4, bean.getRollNo());
		pstmt.setInt(5, bean.getPhysics());
		pstmt.setInt(6, bean.getChemistry());
		pstmt.setInt(7, bean.getMaths());

		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Update  Duplicates: 0  Warnings: 0" + "\n" + "Data Updated");

		pstmt.close();
		conn.close();
	}

	// Delete Query Deleting in Studend_Marksheet
	public void delete(MarksheetBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("delete from Student_Marksheet where id = ?");

		pstmt.setInt(1, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Delete  Duplicates: 0  Warnings: 0" + "\n" + "Data Deleted");

		pstmt.close();
		conn.close();

	}

	// Search Query findbyRollNo in student_Marksheet
	public MarksheetBean findByRollNo(int RollNo) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select * from Student_Marksheet where RollNo = ?");

		pstmt.setInt(1, RollNo);

		ResultSet rs = pstmt.executeQuery();

		MarksheetBean bean = null;

		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setUserName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setRollNo(rs.getInt(5));
			bean.setPhysics(rs.getInt(6));
			bean.setChemistry(rs.getInt(7));
			bean.setMaths(rs.getInt(8));

		}

		pstmt.close();
		conn.close();
		return bean;
	}

	// Search Query findbypk in Student_Marksheet (pk = public key)
	public MarksheetBean findByPk(int pk) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select * from Student_Marksheet where id = ?");

		pstmt.setInt(1, pk);

		ResultSet rs = pstmt.executeQuery();

		MarksheetBean bean = null;

		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setUserName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setRollNo(rs.getInt(5));
			bean.setPhysics(rs.getInt(6));
			bean.setChemistry(rs.getInt(7));
			bean.setMaths(rs.getInt(8));
		}

		pstmt.close();
		conn.close();
		return bean;
	}

	// Search Query Searching Marksheet Details
	public List search(MarksheetBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		StringBuffer sql = new StringBuffer("select * from Student_Marksheet where 1=1");

		if (bean != null) {
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and Name like '" + bean.getName() + "%'");
			}
			if (bean.getRollNo() > 0) {
				sql.append(" and RollNo like '" + bean.getRollNo() + "%'");
			}
		}

		System.out.println("sql Query===> " + sql.toString());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		List list = new ArrayList();

		while (rs.next()) {
			bean = new MarksheetBean();
			bean.setId(rs.getInt(1));
			bean.setName(rs.getString(2));
			bean.setUserName(rs.getString(3));
			bean.setDob(rs.getDate(4));
			bean.setRollNo(rs.getInt(5));
			bean.setPhysics(rs.getInt(6));
			bean.setChemistry(rs.getInt(7));
			bean.setMaths(rs.getInt(8));
			list.add(bean);
		}

		System.out.println("Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Search  Duplicates: 0  Warnings: 0" + "\n" + "Record Displayed");

		conn.close();
		pstmt.close();
		return list;

	}

}
