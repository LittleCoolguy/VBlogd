package com.gao.vblogd.controller;

import com.gao.vblogd.Entity.Article;
import com.gao.vblogd.Entity.RespBean;
import com.gao.vblogd.Entity.User;
import com.gao.vblogd.Utils.Util;
import com.gao.vblogd.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.commons.io.IOUtils; //導入方法依賴的package包/類

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.io.FileOutputStream;
import java.util.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Autowired
    ArticleService articleService;
    /**
     *
     * @param state 表示对应的查询栏(全部、已发表、垃圾箱~~等)
     * @param page  当前展示的页数
     * @param count 每页展示几个数据
     * @param keywords 关键字搜索
     * @return
     */
    @RequestMapping(value = "/all",method = RequestMethod.GET)
    public Map<String,Object> getAllArticle(@RequestParam(value = "state", defaultValue = "-1") Integer state, @RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "count", defaultValue = "6") Integer count, String keywords){
        int totalCount=articleService.getTotalCount(state, Util.getCurrentUser().getId(),keywords);
        List<Article> articles=articleService.getAllArticle(state,page,count,keywords);
        Map<String,Object> mp=new HashMap<>();
        mp.put("totalCount",totalCount);
        mp.put("articles",articles);
        return mp;
    }

    /**
     * 这里是根据aid查看文章，
     * 尝试添加一个记录访问量功能：
     *      根据aid获取uid,在pv表中查找最近时间的访问量，+1操作
     * @param aid
     * @return
     */
    @RequestMapping(value = "/{aid}",method = RequestMethod.GET)
    public Article getArticleByAid(@PathVariable Long aid){
        Long uid = Util.getCurrentUser().getId();
        articleService.addPVByUid(uid);
        return articleService.getArticleByAid(aid);
    }
    @RequestMapping(value = "/dustbin",method = RequestMethod.PUT)
    public RespBean updateArticleState(Long[] aids,Integer state){
            if (articleService.updateArticleState(aids,state)==1)
                return new RespBean("success","删除成功");
            else return new RespBean("fail","删除失败");
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public RespBean addNewArticle(Article article){
           if ( articleService.addNewArticle(article)==1)
               return new RespBean("success", article.getId() + "");
           else
               return new RespBean("error", article.getState() == 0 ? "文章保存失败!" : "文章发表失败!");
    }
    /**
     * 上传图片
     *
     * @return 返回值为图片的地址
     */
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
    public RespBean uploadImg(HttpServletRequest req, MultipartFile image) {
        StringBuffer url = new StringBuffer();
        String filePath = "/blogimg/" + sdf.format(new Date());
        String imgFolderPath = req.getServletContext().getRealPath(filePath);
        File imgFolder = new File(imgFolderPath);
        if (!imgFolder.exists()) {
            imgFolder.mkdirs();
        }
        url.append(req.getScheme())
                .append("://")
                .append(req.getServerName())
                .append(":")
                .append(req.getServerPort())
                .append(req.getContextPath())
                .append(filePath);
        String imgName = UUID.randomUUID() + "_" + image.getOriginalFilename().replaceAll(" ", "");
        try {
            IOUtils.write(image.getBytes(), new FileOutputStream(new File(imgFolder, imgName)));
            url.append("/").append(imgName);
            return new RespBean("success", url.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new RespBean("error", "上传失败!");
    }

    @RequestMapping("/dataStatistics")
    public Map<String,Object> dataStatistics() {
        Map<String, Object> map = new HashMap<>();
        List<String> categories = articleService.getCategories();
        List<Integer> dataStatistics = articleService.getDataStatistics();
        map.put("categories", categories);
        map.put("ds", dataStatistics);
        return map;
    }
}
