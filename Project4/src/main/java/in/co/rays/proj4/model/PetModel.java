package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.PetBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.exception.RecordNotFoundException;
import in.co.rays.proj4.util.JDBCDataSource;

public class PetModel {

	public long nextPK() throws DatabaseException {

		long pk = 0;
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_pet");
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

	public long add(PetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;
		long pk = 0;

		PetBean existBean = findByPetName(bean.getPetName());
		if (existBean != null) {
			throw new DuplicateRecordException("Pet Name Already Exists");
		}

		try {
			pk = nextPK();
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("insert into st_pet values(?, ?, ?, ?, ?, ?, ?, ?, ?)");

			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getPetName());
			pstmt.setString(3, bean.getAnimalType());
			pstmt.setString(4, bean.getAge());
			pstmt.setString(5, bean.getAdoptionStatus());
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

			throw new ApplicationException("Exception : Exception in adding Pet");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return pk;
	}

	public void update(PetBean bean) throws ApplicationException, DuplicateRecordException {

		Connection conn = null;


			PetBean existBean = findByPetName(bean.getPetName());

			if (existBean != null && existBean.getId() != bean.getId()) {
				throw new DuplicateRecordException("Pet Name already exists");
			}

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement(
					"update st_pet set name = ?, type = ?, age = ?, status = ?, created_by = ?, modified_By = ?, created_datetime = ?, modified_Datetime = ? where id = ?");

			pstmt.setString(1, bean.getPetName());
			pstmt.setString(2, bean.getAnimalType());
			pstmt.setString(3, bean.getAge());
			pstmt.setString(4, bean.getAdoptionStatus());
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
			throw new ApplicationException("Exception in updating Pet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public void delete(PetBean bean) throws ApplicationException, RecordNotFoundException {

		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);

			PreparedStatement pstmt = conn.prepareStatement("delete from st_pet where id = ?");

			pstmt.setLong(1, bean.getId());

			pstmt.executeUpdate();
			conn.commit();

		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in Deleting Pet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
	}

	public PetBean findByPK(long pk) throws ApplicationException {

		PetBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_pet where id=?");

			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new PetBean();

				bean.setId(rs.getLong(1));
				bean.setPetName(rs.getString(2));
				bean.setAnimalType(rs.getString(3));
				bean.setAge(rs.getString(4));
				bean.setAdoptionStatus(rs.getString(5));
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

	public PetBean findByPetName(String PetName) throws ApplicationException {

		PetBean bean = null;
		Connection conn = null;

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select * from st_pet where name = ?");

			pstmt.setString(1, PetName);
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {

				bean = new PetBean();

				bean.setId(rs.getLong(1));
				bean.setPetName(rs.getString(2));
				bean.setAnimalType(rs.getString(3));
				bean.setAge(rs.getString(4));
				bean.setAdoptionStatus(rs.getString(5));
				bean.setCreatedBy(rs.getString(6));
				bean.setModifiedBy(rs.getString(7));
				bean.setCreatedDatetime(rs.getTimestamp(8));
				bean.setModifiedDatetime(rs.getTimestamp(9));
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in FindByPetName Pet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return bean;
	}

	public List<PetBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}

	public List<PetBean> search(PetBean bean, int pageNo, int pageSize) throws ApplicationException {

		List<PetBean> list = new ArrayList<PetBean>();
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_pet where 1=1");

		if (bean != null) {

			if (bean.getPetName() != null && bean.getPetName().length() > 0) {
				sql.append(" and name like '" + bean.getPetName() + "%'");
			}

			if (bean.getAnimalType() != null && bean.getAnimalType().length() > 0) {
				sql.append(" and type like '" + bean.getAnimalType() + "%'");
			}

			if (bean.getAdoptionStatus() != null && bean.getAdoptionStatus().length() > 0) {
				sql.append(" and status like '" + bean.getAdoptionStatus() + "%'");
			}
		}

		try {

			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {

				PetBean rb = new PetBean();

				rb.setId(rs.getLong(1));
				rb.setPetName(rs.getString(2));
				rb.setAnimalType(rs.getString(3));
				rb.setAge(rs.getString(4));
				rb.setAdoptionStatus(rs.getString(5));
				rb.setCreatedBy(rs.getString(6));
				rb.setModifiedBy(rs.getString(7));
				rb.setCreatedDatetime(rs.getTimestamp(8));
				rb.setModifiedDatetime(rs.getTimestamp(9));

				list.add(rb);
			}

		} catch (Exception e) {
			throw new ApplicationException("Exception in searching  Pet");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

		return list;
	}
}
