package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ParkingBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ParkingModel {

	public long nextPK() throws DatabaseException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_parking");
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

	public long add(ParkingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		ParkingBean existBean = findByVehicleNumber(bean.getVehicleNumber());
		if (existBean != null) {
			throw new DuplicateRecordException("Vehicle Number Already Exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			pk = nextPK();

			PreparedStatement pstmt = conn.prepareStatement("insert into st_parking values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getVehicleNumber());
			pstmt.setString(3, bean.getSlotNumber());
			pstmt.setDate(4, new java.sql.Date(bean.getEntryTime().getTime()));
			pstmt.setDate(5, new java.sql.Date(bean.getExitTime().getTime()));
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				e.printStackTrace();
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Parking");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(ParkingBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ParkingBean existBean = findByVehicleNumber(bean.getVehicleNumber());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Vehicle Number already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_parking set vehicle_number = ?, slot_number = ?, entry_time = ?, exit_time = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getVehicleNumber());
			pstmt.setString(2, bean.getSlotNumber());
			pstmt.setDate(3, new java.sql.Date(bean.getEntryTime().getTime()));
			pstmt.setDate(4, new java.sql.Date(bean.getExitTime().getTime()));
			pstmt.setString(5, bean.getCreatedBy());
			pstmt.setString(6, bean.getModifiedBy());
			pstmt.setTimestamp(7, bean.getCreatedDatetime());
			pstmt.setTimestamp(8, bean.getModifiedDatetime());
			pstmt.setLong(9, bean.getId());
			pstmt.executeUpdate();
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Parking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_parking where id = ?");

			pstmt.setLong(1, id);

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Parking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public ParkingBean findByPK(long pk) throws ApplicationException {

		ParkingBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_parking where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new ParkingBean();

				bean.setId(rs.getLong(1));
				bean.setVehicleNumber(rs.getString(2));
				bean.setSlotNumber(rs.getString(3));
				bean.setEntryTime(rs.getDate(4));
				bean.setExitTime(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public ParkingBean findByVehicleNumber(String VehicleNumber) throws ApplicationException {

		ParkingBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_parking where vehicle_number = ?");

			pstmt.setString(1, VehicleNumber);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new ParkingBean();

				bean.setId(rs.getLong(1));
				bean.setVehicleNumber(rs.getString(2));
				bean.setSlotNumber(rs.getString(3));
				bean.setEntryTime(rs.getDate(4));
				bean.setExitTime(rs.getDate(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByVehicleNumber");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<ParkingBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<ParkingBean> search(ParkingBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<ParkingBean> list = new ArrayList<ParkingBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_parking where 1=1");

		if (bean != null) {

			if (bean.getVehicleNumber() != null && bean.getVehicleNumber().length() > 0) {
				sql.append(" and vehicle_number like '" + bean.getVehicleNumber() + "%'");
			}

			if (bean.getSlotNumber() != null && bean.getSlotNumber().length() > 0) {
				sql.append(" and slot_number like '" + bean.getSlotNumber() + "%'");
			}

		}
		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				ParkingBean rb = new ParkingBean();

				rb.setId(rs.getLong(1));
				rb.setVehicleNumber(rs.getString(2));
				rb.setSlotNumber(rs.getString(3));
				rb.setEntryTime(rs.getDate(4));
				rb.setExitTime(rs.getDate(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Parking");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
