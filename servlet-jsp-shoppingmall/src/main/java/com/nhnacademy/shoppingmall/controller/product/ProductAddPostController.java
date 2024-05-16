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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/productAddAction.do")
public class ProductAddPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");
        String productName = req.getParameter("product_name");
        BigDecimal productPrice = new BigDecimal(req.getParameter("product_price"));
        String productDescription = req.getParameter("product_description");
        LocalDateTime now = LocalDateTime.now();
        String productImagePath = fileUpload(req, resp);

        Product product = new Product(productId, productName, productPrice, productDescription, now, productImagePath);
        log.debug("Product : {}", product);

        try{
            productService.saveProduct(product);

            for(int i = 1; i <= 3; i ++){
                String categoryId = req.getParameter(String.format("category_id%d", i));
                if(!categoryId.equals("null")){
                    ProductCategoryMapping productCategoryMapping = new ProductCategoryMapping(productId, categoryId);
                    log.debug("ProductCategoryMapping : {}", productCategoryMapping);
                    productCategoryMappingService.saveProductCategoryMapping(productCategoryMapping);
                }
            }

            req.setAttribute("productAddMessage", "제품 등록에 성공하였습니다.");
            return "shop/product/product_add_form";
        } catch (Exception e){
            log.error(e.getMessage());
            req.setAttribute("productAddMessage", "제품 등록에 실패하였습니다.");
            return "shop/product/product_add_form";
        }
    }

    public String fileUpload(HttpServletRequest req, HttpServletResponse resp) {
        String productId = req.getParameter("product_id");

        try{
            for(Part part : req.getParts()){
                String contentDisposition = part.getHeader("Content-Disposition");

                if (contentDisposition.contains("filename=")) {
                    String fileName = extractFileName(contentDisposition, productId);

                    if (fileName == null){
                        return null;
                    }

                    if (part.getSize() > 0) {
                        String path = req.getServletContext().getRealPath("/ProductImage") + File.separator + fileName;
                        if(!fileExtensionCheck(path)){
                            return null;
                        }

                        part.write(path);
                        part.delete();
                        path = "/ProductImage" + File.separator + fileName;
                        return path;
                    }
                } else {
                    String formValue = req.getParameter(part.getName());
                    log.error("{}={}", part.getName(), formValue);
                }
            }
        } catch (ServletException e) {
            log.debug("ServletException occurred: {}", e.getMessage());
        } catch (IOException e) {
            log.debug("IOException occurred: {}", e.getMessage());
        }
        return null;
    }

    // 파일 이름 추출
    private String extractFileName(String contentDisposition, String productId) {
        log.error("contentDisposition:{}",contentDisposition);
        for (String token : contentDisposition.split(";")) {
            if (token.trim().startsWith("filename")) {
                if (token.trim().contains("\"\"")) {
                    return null;
                } else {
                    return productId + token.substring(token.indexOf("."), token.length()-1);
                }
            }
        }
        return null;
    }

    // 파일 업로드 공격을 방지하기위한 업로드 파일에 대한 확장자 체크
    private boolean fileExtensionCheck(String path){
        String[] imageExtensions = {".jpg", ".jpeg", ".png"};
        for(String extension : imageExtensions){
            if(path.endsWith(extension)){
                return true;
            }
        }
        return false;
    }

}
