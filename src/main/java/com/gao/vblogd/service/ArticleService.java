package com.gao.vblogd.service;

import com.gao.vblogd.Entity.Article;
import com.gao.vblogd.Utils.Util;
import com.gao.vblogd.mapper.ArticleMapper;
import com.gao.vblogd.mapper.TagsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ArticleService {
    @Autowired
    ArticleMapper articleMapper;
    @Autowired
    TagsMapper tagsMapper;

    public int getTotalCount(Integer state, Long id, String keywords) {
        return articleMapper.getTotalCount(state,id,keywords);
    }

    public List<Article> getAllArticle(Integer state, Integer page, Integer count, String keywords) {
        int start = (page - 1) * count;
        Long uid = Util.getCurrentUser().getId();
        return articleMapper.getAllArticle(state,start,count,uid,keywords);
    }

    public Article getArticleByAid(Long aid) {
        return articleMapper.getArticleByAid(aid);
    }

    public int updateArticleState(Long[] aids, Integer state) {
        if (state==2){
            //已经在垃圾桶中，直接删除
            return articleMapper.deleteArticleByAid(aids);
        }
        else return articleMapper.updateArticleState(aids,2);
    }

    public int addNewArticle(Article article) {
        int i=0;
        //处理文章摘要
        if (article.getSummary() == null || "".equals(article.getSummary())) {
            //直接截取
            String stripHtml = stripHtml(article.getHtmlContent());
            article.setSummary(stripHtml.substring(0, stripHtml.length() > 50 ? 50 : stripHtml.length()));
        }
        if (article.getId()==-1){
            //添加操作
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            if (article.getState() == 1) {
                //设置发表日期
                article.setPublishDate(timestamp);
            }
            article.setEditTime(timestamp);
            //设置当前用户
            article.setUid(Util.getCurrentUser().getId());
            //是新文章
            i = articleMapper.addNewArticle(article);
        }
        else {
            //更新
            i = articleMapper.updateArticle(article);
        }
        //打标签
        String[] dynamicTags = article.getDynamicTags();
        if (dynamicTags != null && dynamicTags.length > 0) {
            int tags = addTagsToArticle(dynamicTags, article.getId());
            if (tags == -1) {
                return tags;
            }
        }
        return i;
    }
    //添加标签
    private int addTagsToArticle(String[] dynamicTags, Long aid) {
        //1.删除该文章目前所有的标签
        tagsMapper.deleteTagsByAid(aid);
        //2.将上传上来的标签全部存入数据库
        tagsMapper.saveTags(dynamicTags);
        //3.查询这些标签的id
        List<Long> tIds = tagsMapper.getTagsIdByTagName(dynamicTags);
        //4.重新给文章设置标签
        int i = tagsMapper.saveTags2ArticleTags(tIds, aid);
        return i == dynamicTags.length ? i : -1;
    }
    //处理文章摘要
    public String stripHtml(String content) {
        content = content.replaceAll("<p .*?>", "");
        content = content.replaceAll("<br\\s*/?>", "");
        content = content.replaceAll("\\<.*?>", "");
        return content;
    }

    public List<String> getCategories() {
        return articleMapper.getCategories(Util.getCurrentUser().getId());
    }

    public List<Integer> getDataStatistics() {
        return articleMapper.getDataStatistics(Util.getCurrentUser().getId());
    }
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    public void addPVByUid(Long uid) {
        Long pv=articleMapper.getPVByUid(uid);
        pv=pv+1;
        String format = sdf.format(new Date());
        articleMapper.addPVByUid(pv,format,uid);
    }
}
