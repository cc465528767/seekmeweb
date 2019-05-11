package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.ArticleLike;
import com.example.seekmeweb.Bean.Sos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ArticleLikeService extends JpaRepository<ArticleLike,Integer> {
    @Query(value = "select * from tb_article_like articlelike where parentsid=?1 and supporterid =?2"
            , nativeQuery = true)
    public ArticleLike findByParentAndSupporter(Integer parentsid,Integer supporterid);
}
