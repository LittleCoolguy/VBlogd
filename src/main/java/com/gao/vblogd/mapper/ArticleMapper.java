package com.gao.vblogd.mapper;

import com.gao.vblogd.Entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface ArticleMapper {
    int getTotalCount(@Param("state") Integer state, @Param("uid") Long uid, @Param("keywords") String keywords);

    List<Article> getAllArticle(@Param("state") Integer state, @Param("start") Integer start, @Param("count") Integer count, @Param("uid") Long uid,@Param("keywords") String keywords);

    Article getArticleByAid(@Param("aid") Long aid);

    int deleteArticleByAid(@Param("aids") Long aids[]);

    int updateArticleState(@Param("aids") Long aids[], @Param("state") Integer state);

    int addNewArticle(Article article);

    int updateArticle(Article article);

    List<String> getCategories(Long uid);

    List<Integer> getDataStatistics(Long id);

    Long getPVByUid(@Param("uid") Long uid);

    void addPVByUid(@Param("pv")Long pv,@Param("format")String format,@Param("uid") Long uid);
}
