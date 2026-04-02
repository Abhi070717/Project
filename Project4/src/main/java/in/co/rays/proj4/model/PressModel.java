package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PressBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PressModel {
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_press");
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

	public long add(PressBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		PressBean existBean = findByTitle(bean.getTitle());
		if (existBean != null) {
			throw new DuplicateRecordException("Press Release already exists");

		}
		try {

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_press values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getTitle());
			pstmt.setDate(3, new java.sql.Date(bean.getReleaseDate().getTime()));
			pstmt.setString(4, bean.getAuthor());
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
				e.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Press Release");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(PressBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;


			PressBean existBean = findByTitle(bean.getTitle());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Press Release already exists");
			}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_press set title = ?, date = ?, author = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getTitle());
			pstmt.setDate(2, new java.sql.Date(bean.getReleaseDate().getTime()));
			pstmt.setString(3, bean.getAuthor());
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
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Press Release");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PressBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_press where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Press Release");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PressBean findByPK(long pk) throws ApplicationException {

		PressBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_press where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new PressBean();

				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setReleaseDate(rs.getDate(3));
				bean.setAuthor(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Press Release");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public PressBean findByTitle(String title) throws ApplicationException {

		PressBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_press where title = ?");

			pstmt.setString(1, title);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new PressBean();

				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setReleaseDate(rs.getDate(3));
				bean.setAuthor(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByTitle Press Release");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<PressBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<PressBean> search(PressBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<PressBean> list = new ArrayList<PressBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_press where 1 = 1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" and title like '" + bean.getTitle() + "%'");
			}

			if (bean.getReleaseDate() != null) {
				sql.append(" and date = '" + new java.sql.Date(bean.getReleaseDate().getTime()) + "'");
			}

			if (bean.getAuthor() != null && bean.getAuthor().length() > 0) {
				sql.append(" and author like = '" + bean.getAuthor() + "%'");
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

				bean = new PressBean();

				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setReleaseDate(rs.getDate(3));
				bean.setAuthor(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Press Release");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
