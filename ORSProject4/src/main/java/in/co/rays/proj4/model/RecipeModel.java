package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.RecipeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class RecipeModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_recipe");
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

	public long add(RecipeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		try {

			RecipeBean existBean = findByRecipeName(bean.getRecipeName());
			if (existBean != null) {
				throw new DuplicateRecordException("Recipe Name already exists");
			}

			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_recipe values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getRecipeName());
			pstmt.setString(3, bean.getIngredients());
			pstmt.setTimestamp(4, bean.getCookingTime());
			pstmt.setString(5, bean.getDifficultyLevel());
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
				throw new ApplicationException("Exception : add rollback exception " + ex.getMessage());
			}

			throw new ApplicationException("Exception : Exception in adding Recipe");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(RecipeBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;

		try {

			RecipeBean existBean = findByRecipeName(bean.getRecipeName());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Recipe Name already exists");
			}

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_recipe set name = ?, ingredients = ?, time = ?, level = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getRecipeName());
			pstmt.setString(2, bean.getIngredients());
			pstmt.setTimestamp(3, bean.getCookingTime());
			pstmt.setString(4, bean.getDifficultyLevel());
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
			throw new ApplicationException("Exception in updating Recipe");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(long id) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_recipe where id = ?");

			pstmt.setLong(1, id);

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Recipe");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public RecipeBean findByPK(long pk) throws ApplicationException {

		RecipeBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_recipe where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new RecipeBean();

				bean.setId(rs.getLong(1));
				bean.setRecipeName(rs.getString(2));
				bean.setIngredients(rs.getString(3));
				bean.setCookingTime(rs.getTimestamp(4));
				bean.setDifficultyLevel(rs.getString(5));
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

	public RecipeBean findByRecipeName(String RecipeName) throws ApplicationException {

		RecipeBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_recipe where name = ?");

			pstmt.setString(1, RecipeName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new RecipeBean();

				bean.setId(rs.getLong(1));
				bean.setRecipeName(rs.getString(2));
				bean.setIngredients(rs.getString(3));
				bean.setCookingTime(rs.getTimestamp(4));
				bean.setDifficultyLevel(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByRecipeName Recipe");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<RecipeBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}
	
	public List<RecipeBean> search(RecipeBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<RecipeBean> list = new ArrayList<RecipeBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_recipe where 1=1");

		if (bean != null) {

			if (bean.getRecipeName() != null && bean.getRecipeName().length() > 0) {
				sql.append(" and name like '" + bean.getRecipeName () + "%'");
			}

			if (bean.getIngredients() != null && bean.getIngredients().length() > 0) {
				sql.append(" and ingredients like '" + bean.getIngredients() + "%'");
			}

			if (bean.getDifficultyLevel() != null && bean.getDifficultyLevel().length() > 0) {
				sql.append(" and level like '" + bean.getDifficultyLevel() + "%'");
			}
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				RecipeBean rb = new RecipeBean();

				rb.setId(rs.getLong(1));
				rb.setRecipeName(rs.getString(2));
				rb.setIngredients(rs.getString(3));
				rb.setCookingTime(rs.getTimestamp(4));
				rb.setDifficultyLevel(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Recipe");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}

}
