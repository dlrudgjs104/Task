package com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.Impl;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.ProductCategoryMappingRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
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
public class ProductCategoryMappingRepositoryImpl implements ProductCategoryMappingRepository {
    @Override
    public List<ProductCategoryMapping> findAll() {
        List<ProductCategoryMapping> productCategoryMappingList = new ArrayList<>();
        String sql = "select * from product_category_mapping";
        log.debug("Find all product_category mapping");

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            ResultSet rs = psmt.executeQuery();
            while (rs.next()){
                ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping(
                        rs.getString("product_id"),
                        rs.getString("category_id")
                );
                productCategoryMappingList.add(productCategoryMapping);
            }
        } catch (SQLException e) {
            log.debug("Error: {}", e.getMessage());
            throw new RuntimeException(e);
        }
        return productCategoryMappingList;
    }

    @Override
    public Optional<ProductCategoryMapping> findById(String productId, String categoryId) {
        String sql = "select * from product_category_mapping where product_id = ? and category_id = ?";
        log.debug("Find product category mapping: ({}, {})", productId, categoryId);

        ResultSet rs = null;
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            psmt.setString(2, categoryId);
            rs = psmt.executeQuery();
            if(rs.next()){
                ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping(
                        rs.getString("product_id"),
                        rs.getString("category_id")
                );
                return Optional.of(productCategoryMapping);
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
    public int save(ProductCategoryMapping productCategoryMapping) {
        String sql = "insert into product_category_mapping(product_id,category_id) values(?,?)";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productCategoryMapping.getProductId());
            psmt.setString(2, productCategoryMapping.getCategoryId());

            int result = psmt.executeUpdate();
            log.debug("Insert ProductCategoryMapping result: {}", result);

            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int deleteById(String productId, String categoryId) {
        String sql = "delete from product_category_mapping where product_id = ? and category_id = ?";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            psmt.setString(2, categoryId);
            int result = psmt.executeUpdate();
            log.debug("Delete ProductCategoryMapping result: {}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(ProductCategoryMapping productCategoryMapping) {
        return 0;
    }

    @Override
    public int countById(String productId, String categoryId) {
        String sql = "select count(*) from product_category_mapping where product_id = ? and category_id = ?";
        log.debug("Find product category mapping: ({}, {})", productId, categoryId);
        ResultSet rs = null;

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            psmt.setString(2, categoryId);
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

    @Override
    public List<Product> findByCategoryId(String categoryId) {
        List<Product> productList = new ArrayList<>();
        String sql = "select p.* from product p inner join product_category_mapping pcm on p.product_id = pcm.product_id inner join category c on pcm.category_id = c.category_id where c.category_id = ?";
        log.debug("Find product By categoryId: {}", categoryId);

        ResultSet rs = null;
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, categoryId);
            rs = psmt.executeQuery();
            while(rs.next()){
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
        return productList;
    }

    @Override
    public List<Category> findByProductId(String productId) {
        List<Category> categorList = new ArrayList<>();
        String sql = "select c.* from category c inner join product_category_mapping pcm on c.category_id = pcm.category_id inner join product p on pcm.product_id = p.product_id where p.product_id = ?";

        log.debug("Find category By productId: {}", productId);

        ResultSet rs = null;
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, productId);
            rs = psmt.executeQuery();
            while(rs.next()){
                Category category = new Category(
                        rs.getString("category_id"),
                        rs.getString("category_name")
                );
                categorList.add(category);
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
        return categorList;
    }

}
