package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.Impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.Category.service.Impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.domain.ProductCategoryMapping;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.Impl.ProductCategoryMappingRepositoryImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.Impl.ProductCategoryMappingServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.ProductCategoryMappingService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.Impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/productAddAction.do")
public class ProductAddPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String productId = req.getParameter("product_id");
        String productName = req.getParameter("product_name");
        BigDecimal productPrice = new BigDecimal(req.getParameter("product_price"));
        String productDescription = req.getParameter("product_description");
        LocalDateTime now = LocalDateTime.now();

        Product product = new Product(productId, productName, productPrice, productDescription, now);
        log.debug("Product : {}", product);

        ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping(productId, categoryId);
        log.debug("ProductCategoryMapping : {}", productCategoryMapping);

        List<Category> categoryList = categoryService.findAllCategory();
        req.setAttribute("categoryList", categoryList);

        try{
            productService.saveProduct(product);
            productCategoryMappingService.saveProductCategoryMapping(productCategoryMapping);
            req.setAttribute("productAddMessage", "제품 등록에 성공하였습니다.");
            return "shop/product/product_form";
        } catch (Exception e){
            log.error(e.getMessage());
            req.setAttribute("productAddMessage", "제품 등록에 실패하였습니다.");
            return "shop/product/product_form";
        }
    }
}
