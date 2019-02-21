package com.myblog.version3.service;

import com.myblog.version3.entity.Article;

import java.util.List;

public interface articleService {

    Article getByID(String ID);

    List<Article> getByUid(String Uid);

    List<Article> getAll(int pageNum , int pageSize);

    Boolean Insert(Article article);

    Boolean update(Article article);

    Boolean status(Integer status ,String ID);

    Boolean commentStatus(Boolean comment ,String ID);

    Boolean serPrivate(Boolean isPrivate ,String ID);

    Boolean hit(String ID);
}
