package com.nhnacademy.shoppingmall.controller.product;

import com.nhnacademy.shoppingmall.Category.domain.Category;
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
import java.io.File;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/productDelete.do")
public class ProductDeleteController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("productId");
        Product product = productService.getProduct(productId);
        List<Category> categoryList = productCategoryMappingService.getCategoryByProductId(productId);

        for(Category category : categoryList) {
            productCategoryMappingService.deleteProductCategoryMapping(productId,category.getCategoryId());
        }

        productService.deleteProduct(productId);

        fileCheck(req, product.getProductImagePath(), productId);

        return "redirect:/index.do";
    }

    // 기존 이미지 파일 제거
    public void fileCheck(HttpServletRequest req, String path, String productId){
        path = req.getServletContext().getRealPath(path);
        if(!path.equals(req.getServletContext().getRealPath("/ProductImage/no-image.png"))){
            File newFile = new File(path);

            // 기존 파일이 이미 존재한다면 삭제
            if (newFile.exists()) {
                newFile.delete();
            }
        }
    }
}
