package com.gao.vblogd.controller;

import com.gao.vblogd.Entity.RespBean;
import com.gao.vblogd.Entity.category;
import com.gao.vblogd.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public List<category> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public RespBean updateCategory(category category){
        if (categoryService.updateCategory(category)==1)
            return new RespBean("success","更新成功");
        return new RespBean("error", "修改失败!");
    }
    @RequestMapping(value = "", method = RequestMethod.POST)
    public RespBean addNewCate(category category){
        if ("".equals(category.getCateName())||category.getCateName()==null)
            return new RespBean("error", "请输入栏目名称!");
        if(categoryService.addNewCate(category)==1)
            return new RespBean("success","添加成功");
        else return new RespBean("fail","添加失败");
    }
    @RequestMapping(value = "/{ids}",method = RequestMethod.DELETE)
    public RespBean deleteByIds(@PathVariable String ids){
        boolean flag=categoryService.deleteByIds(ids);
        if (flag)
            return new RespBean("success","删除成功");
        else return new RespBean("fail","删除失败");
    }
}
