package com.nhnacademy.shoppingmall.controller.index;

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
import com.nhnacademy.shoppingmall.user.repository.impl.UserRepositoryImpl;
import com.nhnacademy.shoppingmall.user.service.UserService;
import com.nhnacademy.shoppingmall.user.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = {"/index.do"})
public class IndexController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> productList;
        String categoryId = req.getParameter("categoryId");

        if (categoryId == null) {
            productList = productService.findAllProduct();
        } else if(categoryId.equals("all")) {
            productList = productService.findAllProduct();
        } else {
            productList = productCategoryMappingService.getProdcutByCategoryId(categoryId);
        }


        req.setAttribute("productList", productList);

        List<Category> categoryList = categoryService.findAllCategory();
        req.setAttribute("categoryList", categoryList);

        return "shop/main/index";

    }
}