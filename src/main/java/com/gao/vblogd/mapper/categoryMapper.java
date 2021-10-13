package com.gao.vblogd.mapper;

import com.gao.vblogd.Entity.category;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface categoryMapper {
    List<category> getAllCategories();

    int updateCategory(category category);

    int addNewCate(category category);

    boolean deleteByIds(@Param("ids") String[] split);
}
