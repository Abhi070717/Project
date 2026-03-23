package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.FeedbackBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.util.JDBCDataSource;

public class FeedbackModel {

	public long nextPk() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_feedback");
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

	public long add(FeedbackBean bean) throws ApplicationException {

		Connection conn = null;
		long pk = 0;

		try {
			conn = JDBCDataSource.getConnection();
			pk = nextPk();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn
					.prepareStatement("insert into st_feedback values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getFeedbackCode());
			pstmt.setString(3, bean.getUserName());
			pstmt.setString(4, bean.getComments());
			pstmt.setInt(5, bean.getRating());
			pstmt.setDate(6, new java.sql.Date(bean.getFeedbackDate().getTime()));
			pstmt.setString(7, bean.getCreatedBy());
			pstmt.setString(8, bean.getModifiedBy());
			pstmt.setTimestamp(9, bean.getCreatedDatetime());
			pstmt.setTimestamp(10, bean.getModifiedDatetime());
			pstmt.executeUpdate();

			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Add Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in adding Feedback");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;

	}

	public void update(FeedbackBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_feedback set feedback_code = ?, user_name = ?, comments = ?, rating = ?, feedback_date = ?, created_by = ?, modified_by = ?, created_datetime = ?, modified_datetime = ? where id = ?");

			pstmt.setLong(10, bean.getId());
			pstmt.setString(1, bean.getFeedbackCode());
			pstmt.setString(2, bean.getUserName());
			pstmt.setString(3, bean.getComments());
			pstmt.setInt(4, bean.getRating());
			pstmt.setDate(5, new java.sql.Date(bean.getFeedbackDate().getTime()));
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
				ex.printStackTrace();
				throw new ApplicationException("Exeption : Update Rollback Exception" + ex.getMessage());
			}
			throw new ApplicationException("Exeption : Exeption in Updating Feedback");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(FeedbackBean bean) throws ApplicationException {

		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_feedback where id =  ?");

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
			throw new ApplicationException("Exeption : Exeption in Deleting Feedback");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public FeedbackBean findByPK(long pk) throws ApplicationException {

		FeedbackBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_feedback where id = ?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new FeedbackBean();

				bean.setId(rs.getLong(1));
				bean.setFeedbackCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setComments(rs.getString(4));
				bean.setRating(rs.getInt(5));
				bean.setFeedbackDate(rs.getDate(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public FeedbackBean findByCode(String feedbackCode) throws ApplicationException {

		FeedbackBean bean = null;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from st_feedback where feedback_code = ?");
			pstmt.setString(1, feedbackCode);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				bean = new FeedbackBean();

				bean.setId(rs.getLong(1));
				bean.setFeedbackCode(rs.getString(2));
				bean.setUserName(rs.getString(3));
				bean.setComments(rs.getString(4));
				bean.setRating(rs.getInt(5));
				bean.setFeedbackDate(rs.getDate(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDatetime(rs.getTimestamp(9));
				bean.setModifiedDatetime(rs.getTimestamp(10));
			}
		} catch (Exception e) {
			throw new ApplicationException("Exception : Exception in getting Feedback Code");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;

	}

	public List<FeedbackBean> Search(FeedbackBean bean, int pageNo, int pageSize) throws ApplicationException {

		Connection conn = null;
		List<FeedbackBean> list = new ArrayList<FeedbackBean>();

		StringBuffer sql = new StringBuffer("select * from st_feedback where 1 = 1");

		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getFeedbackCode() != null && bean.getFeedbackCode().length() > 0) {
				sql.append(" and feedback_Code like '" + bean.getFeedbackCode() + "%'");
			}

			if (pageSize > 0) {
				pageNo = (pageNo - 1) * pageSize;
				sql.append(" limit " + pageNo + ", " + pageSize);
			}

			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();

				if (rs.next()) {
					bean.setId(rs.getLong(1));
					bean.setFeedbackCode(rs.getString(2));
					bean.setUserName(rs.getString(3));
					bean.setComments(rs.getString(4));
					bean.setRating(rs.getInt(5));
					bean.setFeedbackDate(rs.getDate(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDatetime(rs.getTimestamp(9));
					bean.setModifiedDatetime(rs.getTimestamp(10));
					list.add(bean);
				}
			} catch (Exception e) {
				throw new ApplicationException("Exception : Exception in getting Feedback by Search");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
		}
		return list;
	}

}
