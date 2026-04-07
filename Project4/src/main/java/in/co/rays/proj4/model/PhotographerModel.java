package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PhotographerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PhotographerModel {

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

	public long add(PhotographerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		PhotographerBean existBean = findByName(bean.getPhotographerName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Photographer Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_photographer values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPhotographerName());
			pstmt.setString(3, bean.getEventType());
			pstmt.setLong(4, bean.getCharges());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Add Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in adding Photographer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(PhotographerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		PhotographerBean existBean = findByName(bean.getPhotographerName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Photographer Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_photographer set name = ?, type = ?, charge = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getPhotographerName());
			pstmt.setString(2, bean.getEventType());
			pstmt.setLong(3, bean.getCharges());
			pstmt.setString(4, bean.getCreatedBy());
			pstmt.setString(5, bean.getModifiedBy());
			pstmt.setTimestamp(6, bean.getCreatedDatetime());
			pstmt.setTimestamp(7, bean.getModifiedDatetime());
			pstmt.setLong(8, bean.getId());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Update Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Updating Photographer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PhotographerBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_photographer where id =  ?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : delete Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Deleting Photographer");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PhotographerBean findByPk(long pk) throws ApplicationException {

		PhotographerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_photographer where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new PhotographerBean();

				bean.setId(rs.getLong(1));
				bean.setPhotographerName(rs.getString(2));
				bean.setEventType(rs.getString(3));
				bean.setCharges(rs.getLong(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public PhotographerBean findByName(String name) throws ApplicationException {

		PhotographerBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_photographer where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new PhotographerBean();

				bean.setId(rs.getLong(1));
				bean.setPhotographerName(rs.getString(2));
				bean.setEventType(rs.getString(3));
				bean.setCharges(rs.getLong(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public List<PhotographerBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<PhotographerBean> search(PhotographerBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<PhotographerBean> list = new ArrayList<PhotographerBean>();

		StringBuffer sql = new StringBuffer("select * from st_photographer where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getPhotographerName() != null && bean.getPhotographerName().length() > 0) {
				sql.append(" and name like '" + bean.getPhotographerName() + "%'");
			}
			if (bean.getEventType() != null && bean.getEventType().length() > 0) {
				sql.append(" and type like '" + bean.getEventType() + "%'");
			}
			if (bean.getCharges() > 0.0) {
				sql.append(" and charge like '" + bean.getCharges() + "%'");
			}

		}
		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + "," + pageSize);
		}

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new PhotographerBean();
				bean.setId(rs.getLong(1));
				bean.setPhotographerName(rs.getString(2));
				bean.setEventType(rs.getString(3));
				bean.setCharges(rs.getLong(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Photographer by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
