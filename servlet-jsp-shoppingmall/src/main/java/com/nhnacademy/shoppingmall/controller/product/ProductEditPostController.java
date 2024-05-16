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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/productEditAction.do")
public class ProductEditPostController implements BaseController {
    private final ProductService productService = new ProductServiceImpl(new ProductRepositoryImpl());
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
    private final ProductCategoryMappingService productCategoryMappingService = new ProductCategoryMappingServiceImpl(new ProductCategoryMappingRepositoryImpl());


    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = null;
        String productId = null;
        String productName = null;
        BigDecimal productPrice = null;
        String productDescription = null;
        String productImagePath = null;
        String productImagePathTemp;
        Product product;
        ProductCategoryMapping productCategoryMapping;
        List<Category> categoryList;
        int productCategoryMaxCount = (int) req.getServletContext().getAttribute("PRODUCT_CATEGORY_MAX_COUNT");

        try{
            productId = req.getParameter("product_id");
            productName = req.getParameter("product_name");
            productPrice = new BigDecimal(req.getParameter("product_price"));
            productDescription = req.getParameter("product_description");
            productImagePath = req.getParameter("product_image_path");
            productImagePathTemp = fileUpload(req, resp);
            categoryList = productCategoryMappingService.getCategoryByProductId(productId);

            if(productImagePathTemp != null) {
                productImagePath = productImagePathTemp;
            }

            product = new Product(productId, productName, productPrice, productDescription, null, productImagePath);
            log.debug("Product : {}", product);

            productService.updateProduct(product);

            // 기존 제품카테고리매핑 전부 삭제
            for(int i = 0; i < categoryList.size(); i++){
                categoryId = req.getParameter(String.format("category_id%d", i + 1));
                productCategoryMappingService.deleteProductCategoryMapping(productId, categoryList.get(i).getCategoryId());
            }

            // 수정된 제품카테고리매핑 추가
            for(int i = 0; i < productCategoryMaxCount; i++){
                categoryId = req.getParameter(String.format("category_id%d", i + 1));
                req.setAttribute(String.format("category_id%d", i + 1), categoryId);

                if(!categoryId.equals("null")){
                    productCategoryMapping = new ProductCategoryMapping(productId, categoryId);
                    log.debug("ProductCategoryMapping : {}", productCategoryMapping);
                    productCategoryMappingService.saveProductCategoryMapping(productCategoryMapping);
                }
            }

            req.setAttribute("productEditMessage", "제품 정보 수정에 성공하였습니다.");
            return "shop/product/product_edit_form";
        } catch (Exception e){
            log.error(e.getMessage());
            req.setAttribute("productEditMessage", "제품 정보 수정에 실패하였습니다.");
            return "shop/product/product_edit_form";
        } finally {
            product = new Product(productId, productName, productPrice, productDescription, null, productImagePath);
            log.debug("Product : {}", product);

            productCategoryMapping = new ProductCategoryMapping(productId, categoryId);
            log.debug("ProductCategoryMapping : {}", productCategoryMapping);

            req.setAttribute("productCategoryMapping", productCategoryMapping);

            req.setAttribute("product", product);
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
                    } else if(!fileExtensionCheck(fileName)){
                        throw new RuntimeException();
                    }

                    if (part.getSize() > 0) {
                        String path = req.getServletContext().getRealPath("/ProductImage") + File.separator;
                        fileCheck(path, productId);

                        path += fileName;
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

    // 기존 이미지 파일 제거
    public void fileCheck(String path, String productId){
        String[] imageExtensions = {".jpg", ".jpeg", ".png"};

        for(String extension : imageExtensions){
            String testPath = path + productId + extension;
            File newFile = new File(testPath);

            // 기존 파일이 이미 존재한다면 삭제
            if (newFile.exists()) {
                newFile.delete();
            }
        }
    }

}
