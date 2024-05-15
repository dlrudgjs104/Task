package com.nhnacademy.shoppingmall.product.repository.impl;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.user.domain.User;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

    @Override
    public List<Product> findAll() {
        List<Product> productList = new ArrayList<>();
        String sql = "select * from product";
        log.debug("Find all products: {}", sql);

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql);
             ResultSet rs = psmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("product_price"),
                        rs.getString("product_description"),
                        Objects.nonNull(rs.getTimestamp("product_rdate")) ? rs.getTimestamp("product_rdate").toLocalDateTime() : null,
                        rs.getString("product_image_path")
                );
                productList.add(product);
            }
        } catch (SQLException e) {
            log.debug("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(String productId) {
        String sql = "select * from product where product_id = ?";
        log.debug("Find product by id: {}", sql);

        ResultSet rs = null;
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            rs = psmt.executeQuery();
            if(rs.next()){
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("product_name"),
                        rs.getBigDecimal("product_price"),
                        rs.getString("product_description"),
                        Objects.nonNull(rs.getTimestamp("product_rdate")) ? rs.getTimestamp("product_rdate").toLocalDateTime() : null,
                        rs.getString("product_image_path")
                );
                return Optional.of(product);
            }
        } catch (SQLException e){
            log.debug("error:{}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
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
        String sql = "insert into product (product_id, product_name, product_price, product_description, product_rdate, product_image_path) values (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, product.getProductId());
            psmt.setString(2, product.getProductName());
            psmt.setBigDecimal(3, product.getProductPrice());
            psmt.setString(4, product.getProductDescription());
            psmt.setTimestamp(5, Timestamp.valueOf(product.getProductRdate()));
            psmt.setString(6, product.getProductImagePath() != null ? product.getProductImagePath() : "/ProductImage/no-image.png");

            int result = psmt.executeUpdate();
            log.debug("Insert product reslut:{}", result);

            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteByProductId(String productId) {
        String sql = "delete from product where product_id = ?";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            int result = psmt.executeUpdate();
            log.debug("Delete product reslut:{}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Product product) {
        String sql = "update product set product_name = ?, product_price = ?, product_description = ?, product_image_path = ? where product_id = ?";
        log.debug("Update product: {}", sql);

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, product.getProductName());
            psmt.setBigDecimal(2, product.getProductPrice());
            psmt.setString(3, product.getProductDescription());
            psmt.setString(4, product.getProductImagePath());
            psmt.setString(5, product.getProductId());

            int result = psmt.executeUpdate();
            log.debug("Update product reslut:{}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByProductId(String productId) {
        String sql = "select count(*) from product where product_id = ?";
        log.debug("Count product: {}", sql);
        ResultSet rs = null;

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
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return 0;
    }
}
