package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.SchedulerBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class SchedulerModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_scheduler");
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

	public long add(SchedulerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		SchedulerBean existBean = findByJobName(bean.getJobName());
		if (existBean != null) {
			throw new DuplicateRecordException("Scheduler Name Already Exists");
		}

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_scheduler values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getJobCode());
			pstmt.setString(3, bean.getJobName());
			pstmt.setString(4, bean.getCronExpression());
			pstmt.setString(5, bean.getStatus());
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

			throw new ApplicationException("Exception : Exception in adding Scheduler");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(SchedulerBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;


		SchedulerBean existBean = findByJobName(bean.getJobName());
			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Scheduler Name already exists");
			}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_scheduler set code = ?, name = ?, expression = ?, status = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getJobCode());
			pstmt.setString(2, bean.getJobName());
			pstmt.setString(3, bean.getCronExpression());
			pstmt.setString(4, bean.getStatus());
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
			throw new ApplicationException("Exception in updating Scheduler");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(SchedulerBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_scheduler where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Scheduler");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public SchedulerBean findByPk(long pk) throws ApplicationException {

		SchedulerBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_scheduler where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SchedulerBean();

				bean.setId(rs.getLong(1));
				bean.setJobCode(rs.getString(2));
				bean.setJobName(rs.getString(3));
				bean.setCronExpression(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPk Faculty ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public SchedulerBean findByJobName(String name) throws ApplicationException {

		SchedulerBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_scheduler where name = ?");

			pstmt.setString(1, name);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new SchedulerBean();

				bean.setId(rs.getLong(1));
				bean.setJobCode(rs.getString(2));
				bean.setJobName(rs.getString(3));
				bean.setCronExpression(rs.getString(4));
				bean.setStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByJobName Scheduler");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<SchedulerBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<SchedulerBean> search(SchedulerBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<SchedulerBean> list = new ArrayList<SchedulerBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_scheduler where 1=1");

		if (bean != null) {

			if (bean.getJobCode() != null && bean.getJobCode().length() > 0) {
				sql.append(" and code like '" + bean.getJobCode() + "%'");
			}

			if (bean.getJobName() != null && bean.getJobName().length() > 0) {
				sql.append(" and name like '" + bean.getJobName() + "%'");
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
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

				SchedulerBean rb = new SchedulerBean();

				rb.setId(rs.getLong(1));
				rb.setJobCode(rs.getString(2));
				rb.setJobName(rs.getString(3));
				rb.setCronExpression(rs.getString(4));
				rb.setStatus(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Scheduler");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
