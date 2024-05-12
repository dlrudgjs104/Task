package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public Optional<Product> findById(String productId) {
        String sql = "select * from product where product_id = ?";
        log.debug("Find product by id: {}", sql);

        ResultSet rs = null;
        DbConnectionThreadLocal.initialize();
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            rs = psmt.executeQuery();
            if(rs.next()){
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("product_price"),
                        rs.getString("product_discription"),
                        Objects.nonNull(rs.getTimestamp("product_rdate")) ? rs.getTimestamp("product_rdate").toLocalDateTime() : null
                );
                return Optional.of(product);
            }
        } catch (SQLException e){
            log.debug("error:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
            try{
                rs.close();
            }catch (SQLException e){
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }

    @Override
    public int save(Product product) {
        String sql = "insert into product (product_id, product_name, product_price, product_discription, product_rdate) values (?, ?, ?, ?, ?)";

        DbConnectionThreadLocal.initialize();
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, product.getProductId());
            psmt.setString(2, product.getProductName());
            psmt.setBigDecimal(3, product.getProductPrice());
            psmt.setString(4, product.getProductDescription());
            psmt.setTimestamp(5, Timestamp.valueOf(product.getProductRdate()));

            int result = psmt.executeUpdate();
            log.debug("Insert product reslut:{}", result);

            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    @Override
    public int deleteByProductId(String productId) {
        String sql = "delete from product where product_id = ?";

        DbConnectionThreadLocal.initialize();
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            int result = psmt.executeUpdate();
            log.debug("Delete product reslut:{}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    @Override
    public int update(Product product) {
        String sql = "update product set product_name = ?, product_price = ?, product_description = ? where product_id = ?";
        log.debug("Update product: {}", sql);

        DbConnectionThreadLocal.initialize();
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, product.getProductName());
            psmt.setBigDecimal(2, product.getProductPrice());
            psmt.setString(3, product.getProductDescription());

            int result = psmt.executeUpdate();
            log.debug("Update product reslut:{}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
        }
    }

    @Override
    public int countByProductId(String productId) {
        String sql = "select count(*) from product where product_id = ?";
        log.debug("Count product: {}", sql);
        ResultSet rs = null;

        DbConnectionThreadLocal.initialize();
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            rs = psmt.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e){
            throw new RuntimeException(e);
        } finally {
            DbConnectionThreadLocal.reset();
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
