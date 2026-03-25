package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SupplierBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SupplierModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_supplier");
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

	public long add(SupplierBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		try {

			SupplierBean existBean = findBySupplierName(bean.getName());
			if (existBean != null) {

			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_supplier values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getCategory());
			pstmt.setDate(4, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setInt(5, bean.getPaymentTerm());
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

			throw new ApplicationException("Exception : Exception in adding Supplier");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(SupplierBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			SupplierBean existBean = findBySupplierName(bean.getName());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Supplier Name already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_supplier set name = ?, category = ?, dob = ?, payment_term = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getCategory());
			pstmt.setDate(3, new java.sql.Date(bean.getDob().getTime()));
			pstmt.setInt(4, bean.getPaymentTerm());
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
			throw new ApplicationException("Exception in updating Supplier");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(SupplierBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_supplier where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Supplier");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public SupplierBean findByPK(long pk) throws ApplicationException {

		SupplierBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_supplier where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SupplierBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setPaymentTerm(rs.getInt(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Supplier ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public SupplierBean findBySupplierName(String SupplierName) throws ApplicationException {

		SupplierBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_supplier where name = ?");

			pstmt.setString(1, SupplierName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SupplierBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setPaymentTerm(rs.getInt(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindBySupplierName Supplier");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<SupplierBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<SupplierBean> search(SupplierBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<SupplierBean> list = new ArrayList<SupplierBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_supplier where 1=1");

		if (bean != null) {

			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}

			if (bean.getCategory() != null && bean.getCategory().length() > 0) {
				sql.append(" and Category like '" + bean.getCategory() + "%'");
			}

			if (bean.getPaymentTerm() != null && bean.getPaymentTerm() > 0) {
				sql.append(" and payment_term like '" + bean.getPaymentTerm() + "%'");
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

				bean = new SupplierBean();

				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setCategory(rs.getString(3));
				bean.setDob(rs.getDate(4));
				bean.setPaymentTerm(rs.getInt(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));

				list.add(bean);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Supplier");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}