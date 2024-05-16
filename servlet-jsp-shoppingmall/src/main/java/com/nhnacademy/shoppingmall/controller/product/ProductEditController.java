package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.Impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.Category.service.Impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.repository.Impl.ProductCategoryMappingRepositoryImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.Impl.ProductCategoryMappingServiceImpl;
import com.nhnacademy.shoppingmall.ProductCategoryMapping.service.ProductCategoryMappingService;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import com.nhnacademy.shoppingmall.product.service.Impl.ProductServiceImpl;
import com.nhnacademy.shoppingmall.product.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/productEdit.do")
public class ProductEditController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("productId");
        Product product = productService.getProduct(productId);
        String productImagePath = product.getProductImagePath();
        req.setAttribute("ProductImagePath",productImagePath);

        if(product.getProductImagePath().equals("null")){
            product.setProductImagePath("/ProductImage/no-image.png");
        }
        req.setAttribute("product", product);

        List<Category> categoryList = productCategoryMappingService.getCategoryByProductId(productId);

        for(int i = 0; i < categoryList.size(); i++){
            req.setAttribute(String.format("category_id%d", i + 1), categoryList.get(i).getCategoryId());
        }

        return "shop/product/product_edit_form";
    }
}
