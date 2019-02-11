package com.myblog.version3.service;

import com.myblog.version3.entity.Article;

import java.util.List;

public interface articleService {

    Article getByID(String ID);

    List<Article> getByUid(String Uid);

    List<Article> getAll();

    Boolean Insert(Article article);

    Boolean status(Integer status ,String ID);

    Boolean commentStatus(Integer comment ,String ID);

    Boolean hit(String ID);
}
