package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.BloodBankBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class BloodBankModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_blood_bank");
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

	public long add(BloodBankBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		BloodBankBean existBean = findByBloodGroup(bean.getbloodGroup());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Blood Group already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_blood_bank values(?,?,?,?,?,?,?,?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getbloodGroup());
			pstmt.setInt(3, bean.getunitsAvailable());
			pstmt.setString(4, bean.getLocation());
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
				throw new ApplicationException("Exception : Add Rollback " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add BloodBank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(BloodBankBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		BloodBankBean existBean = findByBloodGroup(bean.getbloodGroup());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Blood Group already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_blood_bank set bgroup=?, unit=?, location=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id = ?");

			pstmt.setString(1, bean.getbloodGroup());
			pstmt.setInt(2, bean.getunitsAvailable());
			pstmt.setString(3, bean.getLocation());
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
				throw new ApplicationException("Exception : Update Rollback " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in updating Blood Bank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(BloodBankBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_blood_bank where id = ?");

			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete Rollback " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in deleting BloodBank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public BloodBankBean findByPK(long pk) throws ApplicationException {

		BloodBankBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_blood_bank where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new BloodBankBean();

				bean.setId(rs.getLong(1));
				bean.setbloodGroup(rs.getString(2));
				bean.setunitsAvailable(rs.getInt(3));
				bean.setLocation(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in findByPK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public BloodBankBean findByBloodGroup(String bloodGroup) throws ApplicationException {

		BloodBankBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_blood_bank where bgroup = ?");

			pstmt.setString(1, bloodGroup);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new BloodBankBean();

				bean.setId(rs.getLong(1));
				bean.setbloodGroup(rs.getString(2));
				bean.setunitsAvailable(rs.getInt(3));
				bean.setLocation(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in findByBloodGroup");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<BloodBankBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<BloodBankBean> search(BloodBankBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<BloodBankBean> list = new ArrayList<>();

		StringBuffer sql = new StringBuffer("select * from st_blood_bank where 1=1");

		if (bean != null) {

			if (bean.getbloodGroup() != null && bean.getbloodGroup().length() > 0) {
				sql.append(" and bgroup like '" + bean.getbloodGroup() + "%'");
			}

			if (bean.getunitsAvailable() != null && bean.getunitsAvailable() > 0) {
				sql.append(" and unit = " + bean.getunitsAvailable());
			}

			if (bean.getLocation() != null && bean.getLocation().length() > 0) {
				sql.append(" and location like '" + bean.getLocation() + "%'");
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
				bean = new BloodBankBean();

				bean.setId(rs.getLong(1));
				bean.setbloodGroup(rs.getString(2));
				bean.setunitsAvailable(rs.getInt(3));
				bean.setLocation(rs.getString(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));

				list.add(bean);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in search BloodBank");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}