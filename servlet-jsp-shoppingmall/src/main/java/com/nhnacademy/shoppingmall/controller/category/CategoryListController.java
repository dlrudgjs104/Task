package com.nhnacademy.shoppingmall.controller.category;

import com.nhnacademy.shoppingmall.Category.domain.Category;
import com.nhnacademy.shoppingmall.Category.repository.Impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.Category.service.CategoryService;
import com.nhnacademy.shoppingmall.Category.service.Impl.CategoryServiceImpl;
import com.nhnacademy.shoppingmall.common.mvc.annotation.RequestMapping;
import com.nhnacademy.shoppingmall.common.mvc.controller.BaseController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RequestMapping(method = RequestMapping.Method.GET,value = "/categoryList.do")
public class CategoryListController implements BaseController {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        CategoryService categoryService = new CategoryServiceImpl(new CategoryRepositoryImpl());
        List<Category> categoryList = categoryService.findAllCategory();

        req.setAttribute("listKind", "category");
        req.setAttribute("categoryList", categoryList);
        return "shop/page/adminpage_form";
    }
}