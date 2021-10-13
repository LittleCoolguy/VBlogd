package com.gao.vblogd.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TagsMapper {
    void deleteTagsByAid(Long aid);

    void saveTags(@Param("tags") String[] dynamicTags);

    List<Long> getTagsIdByTagName(@Param("tagNames") String[] dynamicTags);

    int saveTags2ArticleTags(@Param("tagIds") List<Long> tIds,@Param("aid") Long aid);
}
