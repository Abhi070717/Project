package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CertificateBean;
import in.co.rays.proj4.bean.CompanyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CertificateModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_certificate");
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

	public long add(CertificateBean bean) throws ApplicationException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_certificate values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getCertificateCode());
			pstmt.setString(3, bean.getStudentName());
			pstmt.setString(4, bean.getCourseName());
			pstmt.setDate(5, new java.sql.Date(bean.getIssueDate().getTime()));
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
				throw new ApplicationException("Exeption : Add Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in adding Certificate");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(CertificateBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_certificate set code = ?, student_name = ?, course_name = ?, date = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getCertificateCode());
			pstmt.setString(2, bean.getStudentName());
			pstmt.setString(3, bean.getCourseName());
			pstmt.setDate(4, new java.sql.Date(bean.getIssueDate().getTime()));
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
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Update Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Updating Certificate");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(CertificateBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_certificate where id =  ?");

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
			throw new ApplicationException("Exeption : Exeption in Deleting Certificate");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public CertificateBean findByPK(long pk) throws ApplicationException {

		CertificateBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_certificate where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new CertificateBean();

				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public CertificateBean findByCode(String code) throws ApplicationException {

		CertificateBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_certificate where code = ?");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new CertificateBean();

				bean.setId(rs.getLong(1));
				bean.setCertificateCode(rs.getString(2));
				bean.setStudentName(rs.getString(3));
				bean.setCourseName(rs.getString(4));
				bean.setIssueDate(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Cirtificate_Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public List<CertificateBean> Search(CertificateBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_certificate where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getCertificateCode() != null && bean.getCertificateCode().length() > 0) {
				sql.append(" and Code like '" + bean.getCertificateCode() + "%'");
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + ", " + pageSize);
			}

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					bean = new CertificateBean();
					bean.setId(rs.getLong(1));
					bean.setCertificateCode(rs.getString(2));
					bean.setStudentName(rs.getString(3));
					bean.setCourseName(rs.getString(4));
					bean.setIssueDate(rs.getDate(5));
					bean.setCreatedBy(rs.getString(6));
					bean.setModifiedBy(rs.getString(7));
					bean.setCreatedDatetime(rs.getTimestamp(8));
					bean.setModifiedDatetime(rs.getTimestamp(9));
					list.add(bean);
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception : Exception in getting Certificate by Search");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		return list;
	}
}
