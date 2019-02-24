package com.myblog.version3.controller;

import com.myblog.version3.Tools.Redis;
import com.myblog.version3.mapper.articleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTasks {

    @Autowired
    private Redis redis;

    @Autowired
    private articleMapper articleMapper;

    //每天早上六点执行一次
    @Scheduled(cron = "0 0 6 * * ? ")
    public void synchronousArticleHits(){
        List<String> articles = redis.getArticleList(0,-1);
        if(!articles.isEmpty()){
            for(String article : articles){
                String Aid = article.split("_")[1];
                articleMapper.setHits(Aid ,redis.getArticleHits(Aid));
            }
        }
        redis.emptyArticleList(0,0);
    }
}
