package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ProgressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ProgressModel {

	public long nextPK() {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM ST_PROGRESS");

			ResultSet rs;
			rs = pstmt.executeQuery();

			while (rs.next()) {
				pk = rs.getLong(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pk + 1;

	}

	public long add(ProgressBean bean) {

		Connection conn = null;
		long pk = 0;

		conn = JDBCDataSource.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO ST_PROGRESS VALUES(?,?,?,?,?,?,?)");
			pk = nextPK();

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getDeveloperName());
			pstmt.setString(3, bean.getWork());
			pstmt.setString(4, bean.getTarget());
			pstmt.setString(5, bean.getLastWeek());
			pstmt.setString(6, bean.getCurrentWeek());
			pstmt.setDate(7, new java.sql.Date(bean.getToday().getTime()));
			int i;
			i = pstmt.executeUpdate();

			System.out.println("add" + i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return pk;

	}

	public void update(ProgressBean bean) {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE ST_PROGRESS SET DEVELOPER_NAME =?, WORK=?, TARGET=?, LAST_WEEK=?, CURRENT_Week=?, TODAY=? WHERE ID =?");

			pstmt.setString(1, bean.getDeveloperName());
			pstmt.setString(2, bean.getWork());
			pstmt.setString(3, bean.getTarget());
			pstmt.setString(4, bean.getLastWeek());
			pstmt.setString(5, bean.getCurrentWeek());
			pstmt.setDate(6, new java.sql.Date(bean.getToday().getTime()));
			pstmt.setLong(7, bean.getId());
			int i = pstmt.executeUpdate();

			System.out.println("data update =" + i);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<ProgressBean> search(ProgressBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<ProgressBean> list = new ArrayList<ProgressBean>();

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_PROGRESS WHERE 1=1");

		if (bean != null) {

			if (bean.getDeveloperName() != null && bean.getDeveloperName().length() > 0) {
				sql.append(" AND developer_name like '" + bean.getDeveloperName() + "%' ");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + ", " + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new ProgressBean();
				bean.setId(rs.getInt(1));
				bean.setDeveloperName(rs.getString(2));
				bean.setWork(rs.getString(3));
				bean.setTarget(rs.getString(4));
				bean.setLastWeek(rs.getString(5));
				bean.setCurrentWeek(rs.getString(6));
				bean.setToday(rs.getDate(7));
				list.add(bean);

			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Progress by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;

	}

	public void delete(long id) {
		Connection conn = null;
		conn = JDBCDataSource.getConnection();

		try {
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM ST_PROGRESS WHERE id  = ?");

			pstmt.setLong(1, id);
			int i = pstmt.executeUpdate();
			System.out.println("data delete =" + i);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ProgressBean findbypk(long pk) {

		Connection conn = null;
		ProgressBean bean = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt;
			pstmt = conn.prepareStatement("SELECT * FROM ST_PROGRESS WHERE ID =?");
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ProgressBean();
				bean.setId(rs.getInt(1));
				bean.setDeveloperName(rs.getString(2));
				bean.setWork(rs.getString(3));
				bean.setTarget(rs.getString(4));
				bean.setLastWeek(rs.getString(5));
				bean.setCurrentWeek(rs.getString(6));
				bean.setToday(rs.getDate(7));

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bean;

	}

}
