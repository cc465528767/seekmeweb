package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.Article;
import com.example.seekmeweb.Bean.ArticleLike;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.ArticleLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping({"/articlelike"})
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @RequestMapping({"/add_like"})
    @ResponseBody
    private String addLike(HttpServletRequest httpServletRequest) {
        String supporterid = httpServletRequest.getParameter("supporterid");
        String parentsid = httpServletRequest.getParameter("parentsid");

        int parent_id = Integer.parseInt(parentsid);
        int supporter_id = Integer.parseInt(supporterid);

        ArticleLike exam = articleLikeService.findByParentAndSupporter(parent_id,supporter_id);
        if (exam != null) {
            return Result.create(0, "already", null);
        } else {
            try {
                ArticleLike articleLike = new ArticleLike();
                articleLike.setParentsid(parent_id);
                articleLike.setSupporterid(supporter_id);
                articleLikeService.save(articleLike);
                return Result.create(1, "success", null);
            } catch (Exception e) {
                return Result.create(0, "false", null);
            }
        }
    }

    @RequestMapping({"/test"})
    @ResponseBody
    private String test() {
        return "hello article like test!";
    }

}
