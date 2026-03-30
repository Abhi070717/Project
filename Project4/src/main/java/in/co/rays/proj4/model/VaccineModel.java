package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.VaccineBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class VaccineModel {
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_vaccine");
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

	public long add(VaccineBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		try {

			VaccineBean existBean = findByVaccineName(bean.getVaccineName());
			if (existBean != null) {

			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_vaccine values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getVaccineName());
			pstmt.setString(3, bean.getManufacturer());
			pstmt.setDate(4, new java.sql.Date(bean.getExpiryDate().getTime()));
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

			throw new ApplicationException("Exception : Exception in adding Vaccine");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(VaccineBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			VaccineBean existBean = findByVaccineName(bean.getVaccineName());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Vaccine Name already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_vaccine set name = ?, manufacturer = ?, expiry_date = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getVaccineName());
			pstmt.setString(2, bean.getManufacturer());
			pstmt.setDate(3, new java.sql.Date(bean.getExpiryDate().getTime()));
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
			throw new ApplicationException("Exception in updating Vaccine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(VaccineBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_vaccine where id = ?");

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
			throw new ApplicationException("Exception in Deleting Vaccine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public VaccineBean findByPK(long pk) throws ApplicationException {

		VaccineBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_vaccine where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new VaccineBean();

				bean.setId(rs.getLong(1));
				bean.setVaccineName(rs.getString(2));
				bean.setManufacturer(rs.getString(3));
				bean.setExpiryDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Vaccine ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public VaccineBean findByVaccineName(String VaccineName) throws ApplicationException {

		VaccineBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_vaccine where name = ?");

			pstmt.setString(1, VaccineName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new VaccineBean();

				bean.setId(rs.getLong(1));
				bean.setVaccineName(rs.getString(2));
				bean.setManufacturer(rs.getString(3));
				bean.setExpiryDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByVaccineName Vaccine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<VaccineBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<VaccineBean> search(VaccineBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<VaccineBean> list = new ArrayList<VaccineBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_vaccine where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getVaccineName() != null && bean.getVaccineName().length() > 0) {
				sql.append(" and name like '" + bean.getVaccineName() + "%'");
			}

			if (bean.getManufacturer() != null && bean.getManufacturer().length() > 0) {
				sql.append(" and manufacturer like '" + bean.getManufacturer() + "%'");
			}

			if (bean.getExpiryDate() != null) {
				sql.append(" and expiry_date = '" + new java.sql.Date(bean.getExpiryDate().getTime()) + "'");
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

				bean = new VaccineBean();

				bean.setId(rs.getLong(1));
				bean.setVaccineName(rs.getString(2));
				bean.setManufacturer(rs.getString(3));
				bean.setExpiryDate(rs.getDate(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Vaccine");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
