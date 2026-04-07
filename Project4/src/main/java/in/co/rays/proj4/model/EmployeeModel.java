package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EmployeeBean;
import in.co.rays.proj4.bean.PhotographerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EmployeeModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_photographer");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	// 🔹 Add Method
	public long add(EmployeeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		EmployeeBean existBean = findByName(bean.getName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Name already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("insert into st_employee VALUES (?, ?, ?, ?, ?)");

			ps.setLong(1, pk);
			ps.setString(2, bean.getName());
			ps.setString(3, bean.getDepartment());
			ps.setDouble(4, bean.getSalary());
			ps.setString(5, bean.getStatus());

			ps.executeUpdate();

			System.out.println("Employee Added Successfully");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in add Employee");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	// 🔹 Update Method
	public void update(EmployeeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		EmployeeBean existBean = findByName(bean.getName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Name already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn
					.prepareStatement("UPDATE ST_EMPLOYEE SET name=?, department=?, salary=?, status=? where id=?");

			ps.setString(1, bean.getName());
			ps.setString(2, bean.getDepartment());
			ps.setDouble(3, bean.getSalary());
			ps.setString(4, bean.getStatus());
			ps.setLong(5, bean.getId());

			ps.executeUpdate();

			System.out.println("Employee Updated Successfully");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in update Employee");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// 🔹 Delete Method
	public void delete(EmployeeBean bean) throws ApplicationException {

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("DELETE FROM ST_EMPLOYEE WHERE id=?");

			ps.setLong(1, bean.getId());
			ps.executeUpdate();

			System.out.println("Employee Deleted Successfully");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in delete Employee");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	// 🔹 Find By PK
	public EmployeeBean findByPk(long id) throws ApplicationException {

		EmployeeBean bean = null;

		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_EMPLOYEE WHERE id=?");

			ps.setLong(1, id);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new EmployeeBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
				bean.setDepartment(rs.getString("department"));
				bean.setSalary(rs.getDouble("salary"));
				bean.setStatus(rs.getString("status"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in findByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public EmployeeBean findByName(String name) throws ApplicationException {

		EmployeeBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement ps = conn.prepareStatement("SELECT * FROM ST_EMPLOYEE WHERE name=?");

			ps.setString(1, name);

			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				bean = new EmployeeBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
				bean.setDepartment(rs.getString("department"));
				bean.setSalary(rs.getDouble("salary"));
				bean.setStatus(rs.getString("status"));
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in findByName");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<EmployeeBean> list() throws ApplicationException {
		return search(null, 0, 0);

	}

	public List<EmployeeBean> search(EmployeeBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<EmployeeBean> list = new ArrayList<EmployeeBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_EMPLOYEE WHERE 1=1");

		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name like '" + bean.getName() + "%'");
			}

			if (bean.getDepartment() != null && bean.getDepartment().length() > 0) {
				sql.append(" AND department like '" + bean.getDepartment() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" AND status like '" + bean.getStatus() + "%'");
			}
			if (bean.getSalary() > 0) {
				sql.append(" and salary like '" + bean.getSalary() + "%'");
			}
		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement ps = conn.prepareStatement(sql.toString());

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				bean = new EmployeeBean();

				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("name"));
				bean.setDepartment(rs.getString("department"));
				bean.setSalary(rs.getDouble("salary"));
				bean.setStatus(rs.getString("status"));

				list.add(bean);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}