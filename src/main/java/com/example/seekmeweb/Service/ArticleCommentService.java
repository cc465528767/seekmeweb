package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleCommentService extends JpaRepository<ArticleComment,Integer> {
    @Query(value = "select * from tb_article_comment  where parentsid=?1 and visible = 1 "
            , nativeQuery = true)
    List<ArticleComment> visiblefindByParentid(Integer parent_id);
}
