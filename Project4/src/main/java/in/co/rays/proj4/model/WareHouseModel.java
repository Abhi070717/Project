package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.WareHouseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class WareHouseModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_ware_house");
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

	public long add(WareHouseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		WareHouseBean existBean = findByName(bean.getStaffName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Staff Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_ware_house values(?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getStaffName());
			pstmt.setString(3, bean.getRole());
			pstmt.setDouble(4, bean.getSalary());
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
			throw new ApplicationException("Exeption : Exeption in adding WareHouse");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(WareHouseBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		WareHouseBean existBean = findByName(bean.getStaffName());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("Staff Name already exists");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_ware_house set name = ?, role = ?, salary = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getStaffName());
			pstmt.setString(2, bean.getRole());
			pstmt.setDouble(3, bean.getSalary());
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
			throw new ApplicationException("Exeption : Exeption in Updating WareHouse");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(WareHouseBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_ware_house where id =  ?");

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
			throw new ApplicationException("Exeption : Exeption in Deleting WareHouse");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public WareHouseBean findByPK(long pk) throws ApplicationException {

		WareHouseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_ware_house where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new WareHouseBean();

				bean.setId(rs.getLong(1));
				bean.setStaffName(rs.getString(2));
				bean.setRole(rs.getString(3));
				bean.setSalary(rs.getDouble(4));
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

	public WareHouseBean findByName(String name) throws ApplicationException {

		WareHouseBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_ware_house where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new WareHouseBean();

				bean.setId(rs.getLong(1));
				bean.setStaffName(rs.getString(2));
				bean.setRole(rs.getString(3));
				bean.setSalary(rs.getDouble(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Staff Name");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public List<WareHouseBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<WareHouseBean> search(WareHouseBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<WareHouseBean> list = new ArrayList<WareHouseBean>();

		StringBuffer sql = new StringBuffer("select * from st_ware_house where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getStaffName() != null && bean.getStaffName().length() > 0) {
				sql.append(" and name like '" + bean.getStaffName() + "%'");
			}
			if (bean.getRole() != null && bean.getRole().length() > 0) {
				sql.append(" and role like '" + bean.getRole() + "%'");
			}
			if (bean.getSalary() != null && bean.getSalary() > 0) {
				sql.append(" and salary = " + bean.getSalary());
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
				bean = new WareHouseBean();
				bean.setId(rs.getLong(1));
				bean.setStaffName(rs.getString(2));
				bean.setRole(rs.getString(3));
				bean.setSalary(rs.getDouble(4));
				bean.setCreatedBy(rs.getString(5));
				bean.setModifiedBy(rs.getString(6));
				bean.setCreatedDatetime(rs.getTimestamp(7));
				bean.setModifiedDatetime(rs.getTimestamp(8));
				list.add(bean);
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting WareHouse by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
