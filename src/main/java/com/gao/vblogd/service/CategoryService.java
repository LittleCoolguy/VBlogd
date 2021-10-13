package com.gao.vblogd.service;

import com.gao.vblogd.Entity.category;
import com.gao.vblogd.mapper.categoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CategoryService {
    @Autowired
    categoryMapper categoryMapper;

    public List<category> getAllCategories() {
        return categoryMapper.getAllCategories();
    }

    public int updateCategory(category category) {
        return categoryMapper.updateCategory(category);
    }

    public int addNewCate(category category) {
        return categoryMapper.addNewCate(category);
    }

    public boolean deleteByIds(String ids) {
        String[] split = ids.split(",");
        return categoryMapper.deleteByIds(split);
    }
}
