package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.OtpBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class OtpModel {
	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_otp");
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

	public long add(OtpBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		try {

			OtpBean existBean = findByOtpCode(bean.getOtpCode());
			if (existBean != null) {


			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_otp values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getOtpCode());
			pstmt.setString(3, bean.getMobileNumber());
			pstmt.setDate(4, new java.sql.Date(bean.getExpiryTime().getTime()));
			pstmt.setString(5, bean.getOtpStatus());
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

			throw new ApplicationException("Exception : Exception in adding Otp");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(OtpBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			OtpBean existBean = findByOtpCode(bean.getOtpCode());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Otp Name already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_otp set code = ?, number = ?, time = ?, status = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getOtpCode());
			pstmt.setString(2, bean.getMobileNumber());
			pstmt.setDate(3, new java.sql.Date(bean.getExpiryTime().getTime()));
			pstmt.setString(4, bean.getOtpStatus());
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
			throw new ApplicationException("Exception in updating Otp");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_otp where id = ?");

			pstmt.setLong(1, id);

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Otp");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public OtpBean findByPK(long pk) throws ApplicationException {

		OtpBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_otp where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new OtpBean();

				bean.setId(rs.getLong(1));
				bean.setOtpCode(rs.getString(2));
				bean.setMobileNumber(rs.getString(3));
				bean.setExpiryTime(rs.getDate(4));
				bean.setOtpStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Otp ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public OtpBean findByOtpCode(String OtpName) throws ApplicationException {

		OtpBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_otp where code = ?");

			pstmt.setString(1, OtpName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new OtpBean();

				bean.setId(rs.getLong(1));
				bean.setOtpCode(rs.getString(2));
				bean.setMobileNumber(rs.getString(3));
				bean.setExpiryTime(rs.getDate(4));
				bean.setOtpStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByOtpCode Otp");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<OtpBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<OtpBean> search(OtpBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<OtpBean> list = new ArrayList<OtpBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_otp where 1=1");

		if (bean != null) {

			if (bean.getOtpCode() != null && bean.getOtpCode().length() > 0) {
				sql.append(" and code like '" + bean.getOtpCode() + "%'");
			}

			if (bean.getMobileNumber() != null && bean.getMobileNumber().length() > 0) {
				sql.append(" and time like '" + bean.getMobileNumber() + "%'");
			}

			if (bean.getOtpStatus() != null && bean.getOtpStatus().length() > 0) {
				sql.append(" and status like '" + bean.getOtpStatus() + "%'");
			}
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				OtpBean rb = new OtpBean();

				rb.setId(rs.getLong(1));
				rb.setOtpCode(rs.getString(2));
				rb.setMobileNumber(rs.getString(3));
				rb.setExpiryTime(rs.getDate(4));
				rb.setOtpStatus(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Otp");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}