package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Article;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@RestController
@RequestMapping({"/article"})
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    private ArticleController() {
    }

    @RequestMapping({"/test"})
    @ResponseBody
    private String test() {
        return "hello article test!";
    }

    @RequestMapping({"/insert_article"})
    @ResponseBody
    private String articleInsert(HttpServletRequest httpServletRequest) {
        String title = httpServletRequest.getParameter("title");
        String content = httpServletRequest.getParameter("content");
        String publisher = httpServletRequest.getParameter("publisher");

        Article exam = this.articleService.findByTitle(title);
        if (exam != null) {
            return Result.create(0, "already", null);
        } else {
            try {
                Article article1 = new Article();
                article1.setTitle(title);
                article1.setContent(content);
                article1.setPublisher(publisher);
                article1.setLikenumber(0);
                articleService.save(article1);
                return Result.create(1, "success", null);
            } catch (Exception e) {
                return Result.create(0, "false", null);
            }
        }
    }

    @ResponseBody
    @PostMapping(value = "/find")
    public String searchByUserid(String type){
        int type1 = Integer.parseInt(type);
        List<Article> recordList = articleService.findByType(type1);
        if(recordList.size()==0)
        {
            return Result.create(0,"查找失败",recordList);
        }

        return Result.create(0,"查找成功",recordList);
    }

    @ResponseBody
    @PostMapping(value = "/addlike")
    public String addOneLIke(@RequestParam String paperid){
        int paper_id = Integer.parseInt(paperid);
        Article article = articleService.findByPaperid(paper_id);
        if(article == null)
        {
            return Result.create(0,"点赞添加失败",null);
        }
        else {
            int like = article.getLikenumber();
            article.setLikenumber(like+1);
            articleService.save(article);
            return Result.create(0,"点赞添加成功",article);
        }
    }


    @RequestMapping({"/articleAll"})
    @ResponseBody
    public List<Article> departmentList() {
        return articleService.findAll();
    }
}
