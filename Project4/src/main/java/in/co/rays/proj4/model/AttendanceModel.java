package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.AttendanceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class AttendanceModel {
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_attendance");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getLong(1);
			}

		} catch (Exception e) {
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(AttendanceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		AttendanceBean existBean = findByAttendanceCode(bean.getAttendanceCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Employee Code already exists");
		}

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_attendance values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getAttendanceCode());
			pstmt.setString(3, bean.getEmployeeName());
			pstmt.setDate(4, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(5, bean.getStatus());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Attendance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(AttendanceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		AttendanceBean existBean = findByAttendanceCode(bean.getAttendanceCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Employee Code already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_attendance set attendanceCode = ?, name = ?, date = ?, status = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getAttendanceCode());
			pstmt.setString(2, bean.getEmployeeName());
			pstmt.setDate(3, new java.sql.Date(bean.getDate().getTime()));
			pstmt.setString(4, bean.getStatus());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(AttendanceBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_attendance where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public AttendanceBean findByPk(long pk) throws ApplicationException {

		AttendanceBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_attendance where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new AttendanceBean();

				bean.setId(rs.getLong(1));
				bean.setAttendanceCode(rs.getString(2));
				bean.setEmployeeName(rs.getString(3));
				bean.setDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Attendance ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public AttendanceBean findByAttendanceCode(String AttendanceName) throws ApplicationException {

		AttendanceBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_attendance where attendanceCode = ?");

			pstmt.setString(1, AttendanceName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new AttendanceBean();

				bean.setId(rs.getLong(1));
				bean.setAttendanceCode(rs.getString(2));
				bean.setEmployeeName(rs.getString(3));
				bean.setDate(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByAttendanceCode Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<AttendanceBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<AttendanceBean> search(AttendanceBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<AttendanceBean> list = new ArrayList<AttendanceBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_attendance where 1=1");

		if (bean != null) {

			if (bean.getAttendanceCode() != null && bean.getAttendanceCode().length() > 0) {
				sql.append(" and attendanceCode like '" + bean.getAttendanceCode() + "%'");
			}

			if (bean.getEmployeeName() != null && bean.getEmployeeName().length() > 0) {
				sql.append(" and name like '" + bean.getEmployeeName() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}
		if (pageSize > 0) {
			sql.append(" limit " + ((pageNo - 1) * pageSize) + ", " + pageSize);
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				AttendanceBean rb = new AttendanceBean();

				rb.setId(rs.getLong(1));
				rb.setAttendanceCode(rs.getString(2));
				rb.setEmployeeName(rs.getString(3));
				rb.setDate(rs.getDate(4));
				rb.setStatus(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Attendance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}