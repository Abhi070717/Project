package in.co.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import in.co.bean.UserBean;

public class UserModel {
	ResourceBundle rb = ResourceBundle.getBundle("in.co.rays.bundle.app");

	String Driver = rb.getString("driver");
	String Url = rb.getString("url");
	String Username = rb.getString("username");
	String Password = rb.getString("password");

//nextPk Query in st_user
	public int nextPk() throws Exception {

		int pk = 0;

		Class.forName(Driver);

		Connection conn = DriverManager.getConnection(Url, Username, Password);

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_user");

		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
			pk = rs.getInt(1);
		}

		pstmt.close();
		conn.close();

		return pk + 1;
	}

//Add Query in st_user
	public void add(UserBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		System.out.println("Java is connected with MYSQL Successfully");

		PreparedStatement pstmt = conn.prepareStatement("insert into st_user values(?, ?, ?, ?, ?, ?)");

		pstmt.setInt(1, nextPk());
		pstmt.setString(2, bean.getFirstName());
		pstmt.setString(3, bean.getLastName());
		pstmt.setString(4, bean.getLogin());
		pstmt.setString(5, bean.getPassword());
		pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));
		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Added  Duplicates: 0  Warnings: 0" + "\n" + "Data Inserted");

		pstmt.close();
		conn.close();
	}

//Update Query in st_user
	public void update(UserBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_user set firstName = ?, lastName = ?, login = ?, password = ?, dob = ? where id = ?");

		pstmt.setInt(6, bean.getId());
		pstmt.setString(1, bean.getFirstName());
		pstmt.setString(2, bean.getLastName());
		pstmt.setString(3, bean.getLogin());
		pstmt.setString(4, bean.getPassword());
		pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));

		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Update  Duplicates: 0  Warnings: 0" + "\n" + "Data Updated");

		pstmt.close();
		conn.close();

	}

//Delete Query Deleting in st_user
	public void delete(UserBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");

		pstmt.setInt(1, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Delete  Duplicates: 0  Warnings: 0" + "\n" + "Data Deleted");

		pstmt.close();
		conn.close();

	}

//Search Query findbylogin in st_user
	public UserBean findByLogin(String login) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ?");

		pstmt.setString(1, login);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));

		}

		pstmt.close();
		conn.close();
		return bean;

	}

//Search Query findbypk in st_user (pk = public key)
	public UserBean findByPk(int pk) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id = ?");

		pstmt.setInt(1, pk);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
		}

		pstmt.close();
		conn.close();
		return bean;
	}

//Search Query Authenticate in st_user
	public UserBean authenticate(String login, String password) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ? and password = ?");

		pstmt.setString(1, login);
		pstmt.setString(2, password);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));

		}

		pstmt.close();
		conn.close();
		return bean;

	}

//Search Query Searching User Details
	public List search(UserBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

		if (bean != null) {
			if (bean.getFirstName() != null && bean.getFirstName().length() > 0) {
				sql.append(" and firstName like '" + bean.getFirstName() + "%'");
			}
			if (bean.getLastName() != null && bean.getLastName().length() > 0) {
				sql.append(" and lastName like '" + bean.getLastName() + "%'");
			}
		}

		System.out.println("sql Query " + sql.toString());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());

		ResultSet rs = pstmt.executeQuery();

		List list = new ArrayList();

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			list.add(bean);
		}

		System.out.println("Query OK, The rows affected (0.02 sec)" + "\n"
				+ "Records: Search  Duplicates: 0  Warnings: 0" + "\n" + "Record Displayed");

		pstmt.close();
		conn.close();
		return list;

	}

// Search query Formating table of user details
	public List Search1(UserBean bean) throws Exception {

		Class.forName("com.mysql.cj.jdbc.Driver");

		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbcproject", "root", "root");

		System.out.println("java is connected with mysql successfully....");

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user");
		ResultSet rs = pstmt.executeQuery();

		List list = new ArrayList();
		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstName(rs.getString(2));
			bean.setLastName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			list.add(bean);

			// Header
			System.out.println("------------------------------------------------------------------------------------");
			System.out.printf("| %-3s | %-10s | %-10s | %-20s | %-12s | %-10s |%n", "ID", "FirstName", "LastName",
					"Email", "Password", "DOB");
			System.out.println("------------------------------------------------------------------------------------");

			while (rs.next()) {
				System.out.printf("| %-3d | %-10s | %-10s | %-20s | %-12s | %-10s |%n", rs.getInt(1), rs.getString(2),
						rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6));
			}

//Footer line
			System.out.println("------------------------------------------------------------------------------------");

			System.out.println("Query OK, The rows affected (0.02 sec)" + "\n"
					+ "Records: Search1  Duplicates: 0  Warnings: 0" + "\n" + "Record Displayed");

		}
		pstmt.close();
		conn.close();
		return list;

	}

}
