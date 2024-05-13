package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.Impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.Category.service.Impl.CategoryServiceImpl;
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

@Slf4j
@RequestMapping(method = RequestMapping.Method.POST,value = "/categoryAddAction.do")
public class CategoryAddPostController implements BaseController {
    private final CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String categoryId = req.getParameter("category_id");
        String categoryName = req.getParameter("category_name");


        Category category = new Category(categoryId, categoryName);
        log.debug("Category : {}", category);

        try{
            categoryService.saveCategory(category);
            req.setAttribute("categoryAddMessage", "카테고리 등록에 성공하였습니다.");
            return "shop/category/categoryadd_form";
        } catch (Exception e){
            log.error(e.getMessage());
            req.setAttribute("categoryAddMessage", "카테고리 등록에 실패하였습니다.");
            return "shop/category/categoryadd_form";
        }
    }
}
