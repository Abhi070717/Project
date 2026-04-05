package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.MaintenanceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class MaintenanceModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_maintenance");
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

	public long add(MaintenanceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		MaintenanceBean existBean = findByMaintenanceName(bean.getRequestName());
		if (existBean != null) {
			throw new DuplicateRecordException("Maintenance Name already exists");
		}

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_maintenance values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getRequestName());
			pstmt.setString(3, bean.getIssueType());
			pstmt.setString(4, bean.getLocation());
			pstmt.setDate(5, new java.sql.Date(bean.getRequestDate().getTime()));
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
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Maintenance");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(MaintenanceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		MaintenanceBean existBean = findByMaintenanceName(bean.getRequestName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Maintenance Name already exists");
		}
		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_maintenance set name = ?, type = ?, location = ?, date = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getRequestName());
			pstmt.setString(2, bean.getIssueType());
			pstmt.setString(3, bean.getLocation());
			pstmt.setDate(4, new java.sql.Date(bean.getRequestDate().getTime()));
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
			throw new ApplicationException("Exception in updating Maintenance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(MaintenanceBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_maintenance where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Maintenance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public MaintenanceBean findByPk(long pk) throws ApplicationException {

		MaintenanceBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_maintenance where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new MaintenanceBean();

				bean.setId(rs.getLong(1));
				bean.setRequestName(rs.getString(2));
				bean.setIssueType(rs.getString(3));
				bean.setLocation(rs.getString(4));
				bean.setRequestDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Maintenance ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public MaintenanceBean findByMaintenanceName(String name) throws ApplicationException {

		MaintenanceBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_maintenance where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new MaintenanceBean();

				bean.setId(rs.getLong(1));
				bean.setRequestName(rs.getString(2));
				bean.setIssueType(rs.getString(3));
				bean.setLocation(rs.getString(4));
				bean.setRequestDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByMaintenanceName");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<MaintenanceBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<MaintenanceBean> search(MaintenanceBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<MaintenanceBean> list = new ArrayList<MaintenanceBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_maintenance where 1=1");

		if (bean != null) {

			if (bean.getRequestName() != null && bean.getRequestName().length() > 0) {
				sql.append(" and name like '" + bean.getRequestName() + "%'");
			}

			if (bean.getIssueType() != null && bean.getIssueType().length() > 0) {
				sql.append(" and type like '" + bean.getIssueType() + "%'");
			}

			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" and location like '" + bean.getLocation() + "%'");
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

				MaintenanceBean rb = new MaintenanceBean();

				rb.setId(rs.getLong(1));
				rb.setRequestName(rs.getString(2));
				rb.setIssueType(rs.getString(3));
				rb.setLocation(rs.getString(4));
				rb.setRequestDate(rs.getDate(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Maintenance");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
