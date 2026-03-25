package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.TemplateBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class TemplateModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_template");
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

	public long add(TemplateBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		TemplateBean existBean = findByName(bean.getTemplateName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Template Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_template values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getTemplateName());
			pstmt.setString(3, bean.getFormat());
			pstmt.setDate(4, new java.sql.Date(bean.getCreatedDate().getTime()));
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
			throw new ApplicationException("Exeption : Exeption in adding Template");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(TemplateBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		TemplateBean existBean = findByName(bean.getTemplateName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Template Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_template set name = ?, format = ?, created_date = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getTemplateName());
			pstmt.setString(2, bean.getFormat());
			pstmt.setDate(3, new java.sql.Date(bean.getCreatedDate().getTime()));
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
			throw new ApplicationException("Exeption : Exeption in Updating Template");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(TemplateBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_template where id =  ?");

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
			throw new ApplicationException("Exeption : Exeption in Deleting Template");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public TemplateBean findByPK(long pk) throws ApplicationException {

		TemplateBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_template where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new TemplateBean();

				bean.setId(rs.getLong(1));
				bean.setTemplateName(rs.getString(2));
				bean.setFormat(rs.getString(3));
				bean.setCreatedDate(rs.getDate(4));
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

	public TemplateBean findByName(String name) throws ApplicationException {

		TemplateBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_template where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new TemplateBean();

				bean.setId(rs.getLong(1));
				bean.setTemplateName(rs.getString(2));
				bean.setFormat(rs.getString(3));
				bean.setCreatedDate(rs.getDate(4));
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

	public List<TemplateBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<TemplateBean> search(TemplateBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<TemplateBean> list = new ArrayList<TemplateBean>();

		StringBuffer sql = new StringBuffer("select * from st_template where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getTemplateName() != null && bean.getTemplateName().length() > 0) {
				sql.append(" and name like '" + bean.getTemplateName() + "%'");
			}
			if (bean.getFormat() != null && bean.getFormat().length() > 0) {
				sql.append(" and format like '" + bean.getFormat() + "%'");
			}
			if (bean.getCreatedDate() != null && bean.getCreatedDate().getTime() > 0) {
				sql.append(" and created_date like '" + new java.sql.Date(bean.getCreatedDate().getTime()) + "%'");
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

			while(rs.next()) {
				bean = new TemplateBean();
				bean.setId(rs.getLong(1));
				bean.setTemplateName(rs.getString(2));
				bean.setFormat(rs.getString(3));
				bean.setCreatedDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Template by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
