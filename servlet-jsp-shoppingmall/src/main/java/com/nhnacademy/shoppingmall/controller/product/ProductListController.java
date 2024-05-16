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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/productList.do")
public class ProductListController implements BaseController {
    ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        List<Product> productList = productService.findAllProduct();
        List<List<Category>> productCategoryList = new ArrayList<>();

        for (int i = 0; i < productList.size(); i++) {
            List<Category> categoryList = productCategoryMappingService.getCategoryByProductId(productList.get(i).getProductId());
            productCategoryList.add(categoryList);
        }

        req.setAttribute("listKind", "product");
        req.setAttribute("productList", productList);
        req.setAttribute("productCategoryList", productCategoryList);
        return "shop/page/admin_page_form";
    }
}
