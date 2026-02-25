package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CompanyBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CompanyModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_company");
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

	public long add(CompanyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		try {

			CompanyBean existBean = findByCode(bean.getCode());
			if (existBean != null) {
				throw new DuplicateRecordException("Company Code already exists");
			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_company values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getCode());
			pstmt.setString(3, bean.getName());
			pstmt.setString(4, bean.getHead());
			pstmt.setString(5, bean.getLocation());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();

		} catch (DuplicateRecordException e) {
			throw e;

		} catch (Exception e) {

			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Company");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(CompanyBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			CompanyBean existBean = findByCode(bean.getCode());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Company Code already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_company set code = ?, name = ?, head = ?, location = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getCode());
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getHead());
			pstmt.setString(4, bean.getLocation());
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
					+ "Records: Updated successfully  Duplicates: 0  Warnings: 0");

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_company where id=?");

			pstmt.setLong(1, id);

			int i = pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public CompanyBean findByPK(long pk) throws ApplicationException {

		CompanyBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_company where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new CompanyBean();

				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setHead(rs.getString(4));
				bean.setLocation(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : findByPk rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in FindByPk Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public CompanyBean findByCode(String code) throws ApplicationException {

		CompanyBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_company where code=?");

			pstmt.setString(1, code);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new CompanyBean();

				bean.setId(rs.getLong(1));
				bean.setCode(rs.getString(2));
				bean.setName(rs.getString(3));
				bean.setHead(rs.getString(4));
				bean.setLocation(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : findByCode rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in FindByCode Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<CompanyBean> search(CompanyBean bean) throws ApplicationException {

		List<CompanyBean> list = new ArrayList<>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("SELECT * FROM ST_COMPANY WHERE 1=1");

		if (bean != null) {

			if (bean.getCode() != null && bean.getCode().length() > 0) {
				sql.append(" AND code LIKE '" + bean.getCode() + "%'");
			}

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" AND name LIKE '" + bean.getName() + "%'");
			}

			if (bean.getHead() != null && bean.getHead().length() > 0) {
				sql.append(" AND head LIKE '" + bean.getHead() + "%'");
			}
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				CompanyBean cb = new CompanyBean();

				cb.setId(rs.getLong(1));
				cb.setCode(rs.getString(2));
				cb.setName(rs.getString(3));
				cb.setHead(rs.getString(4));
				cb.setLocation(rs.getString(5));
				cb.setCreatedBy(rs.getString(6));
				cb.setModifiedBy(rs.getString(7));
				cb.setCreatedDatetime(rs.getTimestamp(8));
				cb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(cb);
			}

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Search rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in searching Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
