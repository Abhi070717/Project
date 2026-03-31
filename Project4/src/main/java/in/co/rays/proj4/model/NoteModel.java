package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.NoteBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class NoteModel {

	public Integer nextPk() throws DatabaseException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_note_summary");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new DatabaseException("Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk + 1;
	}

	public long add(NoteBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		int pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_note_summary values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setInt(1, pk);
			pstmt.setLong(2, bean.getNotePayableId());
			pstmt.setString(3, bean.getStatus());
			pstmt.setDouble(4, bean.getTotalPrincipal());
			pstmt.setDouble(5, bean.getTotalInterestPaid());
			pstmt.setDouble(6, bean.getTotalOutstanding());
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Add rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in adding Note");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(NoteBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_note_summary set note_payable_id=?, status=?, total_principal=?, total_interest_paid=?, total_outstanding=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? where id=?");

			pstmt.setLong(1, bean.getNotePayableId());
			pstmt.setString(2, bean.getStatus());
			pstmt.setDouble(3, bean.getTotalPrincipal());
			pstmt.setDouble(4, bean.getTotalInterestPaid());
			pstmt.setDouble(5, bean.getTotalOutstanding());
			pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDatetime());
			pstmt.setTimestamp(9, bean.getModifiedDatetime());
			pstmt.setLong(10, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Update rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Note");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(NoteBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_note_summary where id=?");
			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in deleting Note");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public NoteBean findByPk(long pk) throws ApplicationException {

		Connection conn = null;
		NoteBean bean = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_note_summary where id=?");
			pstmt.setLong(1, pk);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new NoteBean();

				bean.setId(rs.getLong(1));
				bean.setNotePayableId(rs.getLong(2));
				bean.setStatus(rs.getString(3));
				bean.setTotalPrincipal(rs.getDouble(4));
				bean.setTotalInterestPaid(rs.getDouble(5));
				bean.setTotalOutstanding(rs.getDouble(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in getting Note by PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<NoteBean> list() throws ApplicationException {
		return search(null, 0, 0);

	}

	public List<NoteBean> search(NoteBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<NoteBean> list = new ArrayList<NoteBean>();

		StringBuffer sql = new StringBuffer("select * from st_note_summary where 1=1");

		if (bean != null) {

			if (bean.getId() > 0) {
				sql.append(" and id=" + bean.getId());
			}

			if (bean.getNotePayableId() > 0) {
				sql.append(" and note_payable_id=" + bean.getNotePayableId());
			}

			if (bean.getStatus() != null && bean.getStatus().length() > 0) {
				sql.append(" and status like '" + bean.getStatus() + "%'");
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
				bean = new NoteBean();

				bean.setId(rs.getLong(1));
				bean.setNotePayableId(rs.getLong(2));
				bean.setStatus(rs.getString(3));
				bean.setTotalPrincipal(rs.getDouble(4));
				bean.setTotalInterestPaid(rs.getDouble(5));
				bean.setTotalOutstanding(rs.getDouble(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));

				list.add(bean);
			}

			rs.close();
			pstmt.close();

		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception in search Note");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}