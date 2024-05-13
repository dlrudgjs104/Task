package com.nhnacademy.shoppingmall.Category.repository.Impl;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Slf4j
public class CategoryRepositoryImpl implements CategoryRepository {


    @Override
    public Optional<Category> findById(String categoryId) {
        String sql = "select * from category where category_id = ?";
        log.debug("Find category by id: {}", sql);

        ResultSet rs = null;
        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, categoryId);
            rs = psmt.executeQuery();
            if(rs.next()){
                Category category = new Category(
                        rs.getString("category_id"),
                        rs.getString("category_name")

                );
                return Optional.of(category);
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
    public int save(Category category) {
        String sql = "insert into category (category_id, category_name) values (?, ?)";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, category.getCategoryId());
            psmt.setString(2, category.getCategoryName());

            int result = psmt.executeUpdate();
            log.debug("Insert category : {}", result);

            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public int deleteByCategoryId(String categoryId) {
        String sql = "delete from category where category_id = ?";

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, categoryId);
            int result = psmt.executeUpdate();
            log.debug("Delete category : {}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int update(Category category) {
        String sql = "update category set category_id = ?, category_name = ? where category_id = ?";
        log.debug("Update category : {}", sql);

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, category.getCategoryId());
            psmt.setString(2, category.getCategoryName());
            psmt.setString(3, category.getCategoryId());

            int result = psmt.executeUpdate();
            log.debug("Update category : {}", result);
            return result;
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int countByCategoryId(String categoryId) {
        String sql = "select count(*) from category where category_id = ?";
        log.debug("Count category : {}", sql);
        ResultSet rs = null;

        try (PreparedStatement psmt = DbConnectionThreadLocal.getConnection().prepareStatement(sql))
        {
            psmt.setString(1, categoryId);
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
