package in.co.rays.proj4.model;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SessionBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SessionModel {
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_user");
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

	public long add(SessionBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		SessionBean existBean = FindByUserName(bean.getUserName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("User Name already exists");
		}

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_user values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getSessionCode());
			pstmt.setString(3, bean.getUserName());
			pstmt.setDate(4, new java.sql.Date(bean.getLoginTime().getTime()));
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

			throw new ApplicationException("Exception : Exception in adding Session");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(SessionBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		SessionBean existBean = FindByUserName(bean.getUserName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("User Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_user set code = ?, name = ?, time = ?, status = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getSessionCode());
			pstmt.setString(2, bean.getUserName());
			pstmt.setDate(3, new java.sql.Date(bean.getLoginTime().getTime()));
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
			throw new ApplicationException("Exception in updating Session");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(SessionBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Session");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public SessionBean findByPk(long pk) throws ApplicationException {

		SessionBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SessionBean();

				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setLoginTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Session ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public SessionBean FindByUserName(String name) throws ApplicationException {

		SessionBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_user where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SessionBean();

				bean.setId(rs.getLong(1));
				bean.setSessionCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setLoginTime(rs.getDate(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByUserName Session");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<SessionBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<SessionBean> search(SessionBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<SessionBean> list = new ArrayList<SessionBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

		if (bean != null) {

			if (bean.getSessionCode() != null && bean.getSessionCode().length() > 0) {
				sql.append(" and code like '" + bean.getSessionCode() + "%'");
			}

			if (bean.getUserName() != null && bean.getUserName().length() > 0) {
				sql.append(" and name like '" + bean.getUserName() + "%'");
			}
			if (bean.getLoginTime() != null && bean.getLoginTime().getTime() > 0) {
				sql.append(" and time like '" + new java.sql.Date(bean.getLoginTime().getTime()) + "%'");
			}
			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
			}
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				SessionBean rb = new SessionBean();

				rb.setId(rs.getLong(1));
				rb.setSessionCode(rs.getString(2));
				rb.setUserName(rs.getString(3));
				rb.setLoginTime(rs.getDate(4));
				rb.setStatus(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Session");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}