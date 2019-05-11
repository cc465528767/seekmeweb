package com.example.seekmeweb.Controller;


import com.example.seekmeweb.Bean.ArticleComment;
import com.example.seekmeweb.Bean.User;
import com.example.seekmeweb.Result;
import com.example.seekmeweb.Service.ArticleCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping({"/articlecomment"})
public class ArticleCommentController {
    @Autowired
    private ArticleCommentService articleCommentService;

    @RequestMapping({"/add"})
    @ResponseBody
    private String addComment(HttpServletRequest httpServletRequest) {
        String username = httpServletRequest.getParameter("commentusername");
        String parentid = httpServletRequest.getParameter("parentsid");
        String comment =  httpServletRequest.getParameter("comment");


        int parent_id = Integer.parseInt(parentid);
        try {
            ArticleComment articleComment = new ArticleComment();
            articleComment.setComment(comment);
            articleComment.setCommentusername(username);
            articleComment.setParentsid(parent_id);
            articleComment.setVisible(1);
            articleCommentService.save(articleComment);

            return Result.create(1, "评论成功", articleComment);
        } catch (Exception e) {
            return Result.create(0, "评论失败", null);
        }


    }

    @RequestMapping({"/getvisible"})
    @ResponseBody
    private String findComment(HttpServletRequest httpServletRequest) {
        String parentid = httpServletRequest.getParameter("parentsid");


        int parent_id = Integer.parseInt(parentid);
        List<ArticleComment> CommentList = new ArrayList<ArticleComment>();
        CommentList = articleCommentService.visiblefindByParentid(parent_id);
        if (CommentList.size() == 0) {
            return Result.create(0, "查找失败", null);
        }
        return Result.create(0,"查找成功",CommentList);
    }


    @RequestMapping({"/test"})
    @ResponseBody
    private String test() {
        return "hello article comment test!";
    }

}
