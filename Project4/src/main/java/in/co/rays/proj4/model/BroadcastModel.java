package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BroadcastBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BroadcastModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		conn = JDBCDataSource.getConnection();
		try {
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_broadcast");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getLong(1);
			}
		} catch (SQLException e) {
			throw new DatabaseException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}
	
	public long add(BroadcastBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;
		
		BroadcastBean existBean = findByCode(bean.getBroadcastCode());

		if (existBean != null) {
			throw new DuplicateRecordException("Broadcast Already Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_broadcast values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getBroadcastCode());
			pstmt.setString(3, bean.getMessageTitle());
			pstmt.setString(4, bean.getMessageContent());
			pstmt.setDate(5, new java.sql.Date(bean.getBroadcastTime().getTime()));
			pstmt.setString(6, bean.getBroadcastStatus());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Add Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in adding Broadcast");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}
	
	public void update(BroadcastBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		BroadcastBean existBean = findByCode(bean.getBroadcastCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Broadcast Already Exist");
		}
		
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("update st_broadcast set code = ?, title = ?, content = ?, time = ?, status = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setLong(10, bean.getId());
			pstmt.setString(1, bean.getBroadcastCode());
			pstmt.setString(2, bean.getMessageTitle());
			pstmt.setString(3, bean.getMessageContent());
			pstmt.setDate(4, new java.sql.Date(bean.getBroadcastTime().getTime()));
			pstmt.setString(5, bean.getBroadcastStatus());
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
				ex.printStackTrace();
				throw new ApplicationException("Exeption : update Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Updating Broadcast");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public void delete(BroadcastBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("delete from st_broadcast where id = ?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Delete Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Deleting Broadcast");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public BroadcastBean findByPK(long pk) throws ApplicationException {

		BroadcastBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_broadcast where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new BroadcastBean();

				bean.setId(rs.getLong(1));
				bean.setBroadcastCode(rs.getString(2));
				bean.setMessageTitle(rs.getString(3));
				bean.setMessageContent(rs.getString(4));
				bean.setBroadcastTime(rs.getDate(5));
				bean.setBroadcastStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public BroadcastBean findByCode(String BroadcastCode) throws ApplicationException {

		BroadcastBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_broadcast where code = ?");

			pstmt.setString(1, BroadcastCode);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new BroadcastBean();

				bean.setId(rs.getLong(1));
				bean.setBroadcastCode(rs.getString(2));
				bean.setMessageTitle(rs.getString(3));
				bean.setMessageContent(rs.getString(4));
				bean.setBroadcastTime(rs.getDate(5));
				bean.setBroadcastStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting BroadcastCode");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}
	
	public List<BroadcastBean> Search(BroadcastBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<BroadcastBean> list = new ArrayList<BroadcastBean>();

		StringBuffer sql = new StringBuffer("select * from st_broadcast where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getBroadcastCode() != null && bean.getBroadcastCode().length() > 0) {
				sql.append(" and code like '" + bean.getBroadcastCode() + "%'");
			}
			if (bean.getBroadcastTime() != null) {
				sql.append(" and time = '" + new java.sql.Date(bean.getBroadcastTime().getTime()) + "'");
			}
			if (bean.getBroadcastStatus() != null && bean.getBroadcastStatus().length() > 0) {
				sql.append(" and status like '" + bean.getBroadcastStatus() + "%'");
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
				bean = new BroadcastBean();
				bean.setId(rs.getLong(1));
				bean.setBroadcastCode(rs.getString(2));
				bean.setMessageTitle(rs.getString(3));
				bean.setMessageContent(rs.getString(4));
				bean.setBroadcastTime(rs.getDate(5));
				bean.setBroadcastStatus(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Broadcast by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
}
