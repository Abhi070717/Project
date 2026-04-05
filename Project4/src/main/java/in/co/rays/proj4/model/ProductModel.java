package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ProductBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ProductModel {

    public long nextPK() throws DatabaseException {

        long pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_product");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                pk = rs.getLong(1);
            }

        } catch (Exception e) {
            throw new DatabaseException("Exception in getting PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk + 1;
    }

    public long add(ProductBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        long pk = 0;

        ProductBean existBean = findByProductName(bean.getProductName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Product already exists");
        }

        try {
            pk = nextPK();
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "insert into st_product values(?, ?, ?, ?, ?)");

            pstmt.setLong(1, pk);
            pstmt.setString(2, bean.getProductName());
            pstmt.setString(3, bean.getCategory());
            pstmt.setDouble(4, bean.getPrice());   // 🔥 Double use
            pstmt.setInt(5, bean.getStock());

            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error " + ex.getMessage());
            }
            throw new ApplicationException("Exception in adding Product");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return pk;
    }

    public void update(ProductBean bean) throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        ProductBean existBean = findByProductName(bean.getProductName());

        if (existBean != null && existBean.getId() != bean.getId()) {
            throw new DuplicateRecordException("Product already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement(
                    "update st_product set productName=?, category=?, price=?, stock=? where id=?");

            pstmt.setString(1, bean.getProductName());
            pstmt.setString(2, bean.getCategory());
            pstmt.setDouble(3, bean.getPrice());  // 🔥 Double
            pstmt.setInt(4, bean.getStock());
            pstmt.setLong(5, bean.getId());

            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                throw new ApplicationException("Rollback error " + ex.getMessage());
            }
            throw new ApplicationException("Exception in updating Product");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public void delete(ProductBean bean) throws ApplicationException {

        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement pstmt = conn.prepareStatement("delete from st_product where id=?");
            pstmt.setLong(1, bean.getId());

            pstmt.executeUpdate();
            conn.commit();

        } catch (Exception e) {
            throw new ApplicationException("Exception in deleting Product");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    public ProductBean findByPk(long pk) throws ApplicationException {

        ProductBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement("select * from st_product where id=?");

            pstmt.setLong(1, pk);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new ProductBean();

                bean.setId(rs.getLong(1));
                bean.setProductName(rs.getString(2));
                bean.setCategory(rs.getString(3));
                bean.setPrice(rs.getDouble(4));  // 🔥 Double
                bean.setStock(rs.getInt(5));
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in FindByPk Product");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }

    public ProductBean findByProductName(String name) throws ApplicationException {

        ProductBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(
                    "select * from st_product where productName=?");

            pstmt.setString(1, name);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                bean = new ProductBean();

                bean.setId(rs.getLong(1));
                bean.setProductName(rs.getString(2));
                bean.setCategory(rs.getString(3));
                bean.setPrice(rs.getDouble(4)); // 🔥 Double
                bean.setStock(rs.getInt(5));
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in FindByProductName");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return bean;
    }
    
    public List list() throws ApplicationException {
		return search(null, 0, 0);
    	
    }

    public List<ProductBean> search(ProductBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        List<ProductBean> list = new ArrayList<>();
        Connection conn = null;

        StringBuffer sql = new StringBuffer("select * from st_product where 1=1");

        if (bean != null) {

            if (bean.getProductName() != null && bean.getProductName().length() > 0) {
                sql.append(" and productName like '" + bean.getProductName() + "%'");
            }

            if (bean.getCategory() != null && bean.getCategory().length() > 0) {
                sql.append(" and category like '" + bean.getCategory() + "%'");
            }
        }

        if (pageSize > 0) {
            sql.append(" limit " + ((pageNo - 1) * pageSize) + ", " + pageSize);
        }

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {

                ProductBean rb = new ProductBean();

                rb.setId(rs.getLong(1));
                rb.setProductName(rs.getString(2));
                rb.setCategory(rs.getString(3));
                rb.setPrice(rs.getDouble(4));
                rb.setStock(rs.getInt(5));

                list.add(rb);
            }

        } catch (Exception e) {
            throw new ApplicationException("Exception in searching Product");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }

        return list;
    }
}