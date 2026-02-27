package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.EventBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class EventModel {

	public long nextPk() throws DatabaseException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_event");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
			pk = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return pk + 1;
	}

	public long add(EventBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		EventBean existBean = findByVenue(bean.getVenue());

		if (existBean != null) {
			throw new DuplicateRecordException("Venue Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_event values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getTitle());
			pstmt.setString(3, bean.getDescription());
			pstmt.setDate(4, new java.sql.Date(bean.getEventDate().getTime()));
			pstmt.setString(5, bean.getStartTime());
			pstmt.setString(6, bean.getEndTime());
			pstmt.setString(7, bean.getVenue());
			pstmt.setString(8, bean.getOrganizerName());
			pstmt.setString(9, bean.getContactEmail());
			pstmt.setString(10, bean.getContectMobile());
			pstmt.setString(11, bean.getStatus());
			pstmt.setString(12, bean.getCreatedBy());
			pstmt.setString(13, bean.getModifiedBy());
			pstmt.setTimestamp(14, bean.getCreatedDatetime());
			pstmt.setTimestamp(15, bean.getModifiedDatetime());
			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
					+ "Records: Added successfully Duplicates: 0  Warnings: 0");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in add Event");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(EventBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		EventBean existBean = findByVenue(bean.getVenue());

		if (existBean != null && existBean.getId() != bean.getId()) {
			throw new DuplicateRecordException("venue Already Exist");
		}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_event set title = ?, description = ?, event_date = ?, start_time = ?, end_time = ?, venue = ?, organizer_name = ?, contact_email = ?, contact_mobile = ?, contact_mobile = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setString(1, bean.getTitle());
			pstmt.setString(2, bean.getDescription());
			pstmt.setDate(3, new java.sql.Date(bean.getEventDate().getTime()));
			pstmt.setString(4, bean.getStartTime());
			pstmt.setString(5, bean.getEndTime());
			pstmt.setString(6, bean.getVenue());
			pstmt.setString(7, bean.getOrganizerName());
			pstmt.setString(8, bean.getContactEmail());
			pstmt.setString(9, bean.getContectMobile());
			pstmt.setString(10, bean.getStatus());
			pstmt.setString(11, bean.getCreatedBy());
			pstmt.setString(12, bean.getModifiedBy());
			pstmt.setTimestamp(13, bean.getCreatedDatetime());
			pstmt.setTimestamp(14, bean.getModifiedDatetime());
			pstmt.setLong(15, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
					+ "Records: Updated successfully Duplicates: 0  Warnings: 0");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in update Event");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(EventBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_event where id = ?");

			pstmt.setLong(1, bean.getId());
			int i = pstmt.executeUpdate();
			conn.commit();

			System.out.println(i + " Query OK, The rows affected (0.02 sec)" + "\n"
					+ "Records: Deleted successfully Duplicates: 0  Warnings: 0");
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception : Exception in Deleting Event");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public EventBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		EventBean bean = null;

		StringBuffer sb = new StringBuffer("select * from st_event where id = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EventBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setEventDate(rs.getDate(4));
				bean.setStartTime(rs.getString(5));
				bean.setEndTime(rs.getString(6));
				bean.setVenue(rs.getString(7));
				bean.setOrganizerName(rs.getString(8));
				bean.setContactEmail(rs.getString(9));
				bean.setContectMobile(rs.getString(10));
				bean.setStatus(rs.getString(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Event by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public EventBean findByVenue(String Venue) throws ApplicationException {

		Connection conn = null;
		EventBean bean = null;

		StringBuffer sb = new StringBuffer("select * from st_event where venue = ?");

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, Venue);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new EventBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setEventDate(rs.getDate(4));
				bean.setStartTime(rs.getString(5));
				bean.setEndTime(rs.getString(6));
				bean.setVenue(rs.getString(7));
				bean.setOrganizerName(rs.getString(8));
				bean.setContactEmail(rs.getString(9));
				bean.setContectMobile(rs.getString(10));
				bean.setStatus(rs.getString(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Event by Venue");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;
	}

	public List<EventBean> search(EventBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_event where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getTitle() != null && bean.getTitle().length() > 0) {
				sql.append(" and title like '" + bean.getTitle() + "%'");
			}
			if (bean.getDescription() != null && bean.getDescription().length() > 0) {
				sql.append(" and description like '" + bean.getDescription() + "%'");
			}
			if (bean.getEventDate() != null && bean.getEventDate().getTime() > 0) {
				sql.append(" and event_date like '" + bean.getEventDate() + "%'");
			}
			if (bean.getStartTime() != null && bean.getStartTime().length() > 0) {
				sql.append(" and start_time like '" + bean.getStartTime() + "%'");
			}
			if (bean.getVenue() != null && bean.getVenue().length() > 0) {
				sql.append(" and venue like '" + bean.getVenue() + "%'");
			}
			if (bean.getOrganizerName() != null && bean.getOrganizerName().length() > 0) {
				sql.append(" and organizer_name like '" + bean.getOrganizerName() + "%'");
			}
			if (bean.getContactEmail() != null && bean.getContactEmail().length() > 0) {
				sql.append(" and contact_email like '" + bean.getContactEmail() + "%'");
			}
			if (bean.getContectMobile() != null && bean.getContectMobile().length() > 0) {
				sql.append(" and contact_mobile like '" + bean.getContectMobile() + "%'");
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
				bean = new EventBean();
				bean.setId(rs.getLong(1));
				bean.setTitle(rs.getString(2));
				bean.setDescription(rs.getString(3));
				bean.setEventDate(rs.getDate(4));
				bean.setStartTime(rs.getString(5));
				bean.setEndTime(rs.getString(6));
				bean.setVenue(rs.getString(7));
				bean.setOrganizerName(rs.getString(8));
				bean.setContactEmail(rs.getString(9));
				bean.setContectMobile(rs.getString(10));
				bean.setStatus(rs.getString(11));
				bean.setCreatedBy(rs.getString(12));
				bean.setModifiedBy(rs.getString(13));
				bean.setCreatedDatetime(rs.getTimestamp(14));
				bean.setModifiedDatetime(rs.getTimestamp(15));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in getting Event by Search");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return list;
	}
}
