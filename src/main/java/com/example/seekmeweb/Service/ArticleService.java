package com.example.seekmeweb.Service;

import com.example.seekmeweb.Bean.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleService extends JpaRepository<Article,Integer> {

    public Article findByTitle(String title);
    public Article findByPaperid(Integer paperid);

    List<Article> findByType(int type);
}
