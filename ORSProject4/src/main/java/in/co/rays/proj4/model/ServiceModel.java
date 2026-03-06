package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ServiceBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ServiceModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_service");
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				pk = rs.getLong(1);
			}

			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;

	}

	public long add(ServiceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		ServiceBean existBean = findByServiceCode(bean.getServiceCode());

		if (existBean != null) {
			throw new DuplicateRecordException("Service Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_service values(?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getServiceCode());
			pstmt.setLong(3, bean.getCarId());
			pstmt.setDate(4, new java.sql.Date(bean.getServiceDate().getTime()));
			pstmt.setString(5, bean.getServiceStatus());
			pstmt.executeUpdate();
			System.out.println("Service Status : " + bean.getServiceStatus());
			conn.commit();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Add Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in adding Service");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(ServiceBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		ServiceBean existBean = findByServiceCode(bean.getServiceCode());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Service Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_service set service_code = ?, car_id = ?, service_date = ?, service_status = ? Where id = ?");

			pstmt.setLong(5, bean.getId());
			pstmt.setString(1, bean.getServiceCode());
			pstmt.setLong(2, bean.getCarId());
			pstmt.setDate(3, new java.sql.Date(bean.getServiceDate().getTime()));
			pstmt.setString(4, bean.getServiceStatus());
			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Update Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Updating Service");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public void delete(ServiceBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_service where id = ?");
			pstmt.setLong(1, bean.getId());
			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Delete Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Deleting Service");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}

	public ServiceBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		ServiceBean bean = null;

		StringBuffer sb = new StringBuffer("select * from st_service where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ServiceBean();
				bean.setId(rs.getLong(1));
				bean.setServiceCode(rs.getString(2));
				bean.setCarId(rs.getLong(3));
				bean.setServiceDate(rs.getDate(4));
				bean.setServiceStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Service by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public ServiceBean findByServiceCode(String serviceCode) throws ApplicationException {

		Connection conn = null;
		ServiceBean bean = null;

		StringBuffer sb = new StringBuffer("select * from st_service where service_code = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, serviceCode);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new ServiceBean();
				bean.setId(rs.getLong(1));
				bean.setServiceCode(rs.getString(2));
				bean.setCarId(rs.getLong(3));
				bean.setServiceDate(rs.getDate(4));
				bean.setServiceStatus(rs.getString(5));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Service by Service Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<ServiceBean> Search(ServiceBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<ServiceBean> list = new ArrayList<ServiceBean>();

		StringBuffer sql = new StringBuffer("select * from st_service where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getServiceCode() != null && bean.getServiceCode().length() > 0) {
				sql.append(" and service_code like '" + bean.getServiceCode() + "%'");
			}
			if (bean.getCarId() > 0) {
				sql.append(" and car_id = " + bean.getCarId());
			}
			if (bean.getServiceDate() != null) {
				sql.append(" and service_date = '" + new java.sql.Date(bean.getServiceDate().getTime()) + "'");
			}
			if (bean.getServiceStatus() != null && bean.getServiceStatus().length() > 0) {
				sql.append(" and service_status like '" + bean.getServiceStatus() + "%'");
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
				bean = new ServiceBean();
				bean.setId(rs.getLong(1));
				bean.setServiceCode(rs.getString(2));
				bean.setCarId(rs.getLong(3));
				bean.setServiceDate(rs.getDate(4));
				bean.setServiceStatus(rs.getString(5));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Service by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;

	}
}
