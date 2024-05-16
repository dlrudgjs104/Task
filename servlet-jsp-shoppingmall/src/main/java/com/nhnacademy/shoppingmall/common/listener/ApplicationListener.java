package com.nhnacademy.shoppingmall.common.listener;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.Impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.Category.service.Impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.Impl.ProductCategoryMappingRepositoryImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.Impl.ProductCategoryMappingServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.ProductCategoryMappingService;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.Impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.user.domain.User;
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
public class ApplicationListener implements ServletContextListener {
    private final UserService userService = new UserServiceImpl(new UserRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());
    private final int PRODUCT_CATEGORY_MAX_COUNT = 3;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //todo#12 application 시작시 테스트 계정인 admin,user 등록합니다. 만약 존재하면 등록하지 않습니다.
        DbConnectionThreadLocal.initialize();

        User admin = new User("admin", "admin", "12345", "20240501", User.Auth.ROLE_ADMIN, 1_000_000, LocalDateTime.now(), null);
        User user = new User("user", "user", "12345", "20240501", User.Auth.ROLE_USER, 1_000_000, LocalDateTime.now(), null);

        if(userService.getUser(admin.getUserId()) == null){
            userService.saveUser(admin);
            log.info("Admin user created.");
        }

        if(userService.getUser(user.getUserId()) == null){
            userService.saveUser(user);
            log.info("Regular user created.");
        }

        String[] names = {"모자", "상의", "하의", "신발"};

        for(int i = 0; i < names.length; i++){
            Category category = new Category(String.valueOf(i), names[i]);

            if(categoryService.getCategory(category.getCategoryId()) == null){
                categoryService.saveCategory(category);
                log.info("Category created:{}", "categoryId: " + i + ", categoryName: " + names[i]);
            }
        }

        names = new String[]{"비니", "셔츠", "청바지", "구두"};
        String[] descriptions = {"평범한 비니", " 얼룩덜룩한 셔츠", "찢어진 청바지", " 검은색 구두"};

        for(int i = 0; i < names.length; i++){
            Product product = new Product(String.valueOf(i), names[i], new BigDecimal(10_000), descriptions[i], LocalDateTime.now(), null);

            if(productService.getProduct(product.getProductId()) == null){
                productService.saveProduct(product);
                log.info("Product created:{}", "productId: " + i + ", productName: " + names[i] + ", productPrice: 10000, productDescription: " + descriptions[i]);
            }
        }

        for(int i = 0; i < names.length; i++){
            ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping(String.valueOf(i),String.valueOf(i));

            if(productCategoryMappingService.getProductCategoryMapping(productCategoryMapping.getProductId(), productCategoryMapping.getCategoryId()) == null){
                productCategoryMappingService.saveProductCategoryMapping(productCategoryMapping);
                log.info("ProductCategoryMapping created: {}", productCategoryMapping);
            }
        }

        // 카테고리 리스트 등록
        List<Category> categoryList = categoryService.findAllCategory();
        sce.getServletContext().setAttribute("categoryList", categoryList);
        // 카테고리 최대 선택 개수 등록
        sce.getServletContext().setAttribute("PRODUCT_CATEGORY_MAX_COUNT", PRODUCT_CATEGORY_MAX_COUNT);

        DbConnectionThreadLocal.reset();
    }
}
