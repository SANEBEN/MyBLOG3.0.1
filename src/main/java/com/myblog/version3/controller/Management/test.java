package com.myblog.version3.controller.Management;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.JsonObject;
import com.myblog.version3.Tools.Redis;
import com.myblog.version3.entity.Article;
import com.myblog.version3.entity.Form.Form;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/test")
@Api("表单上传测试接口")
public class test {

    private Logger logger = LoggerFactory.getLogger(test.class);
    @Autowired
    com.myblog.version3.mapper.articleMapper articleMapper;

    @Autowired
    com.myblog.version3.mapper.userMapper userMapper;

    @Autowired
    Redis redis;

    @PostMapping(value = "/upload")
    public String upload(@Valid Form message) throws IOException {
        MultipartFile file = message.getImage();
        InputStream stream = file.getInputStream();
        BufferedInputStream inputStream = new BufferedInputStream(stream);
        String path = "D:\\test";
        byte[] bs = new byte[1024];
        // 读取到的数据长度
        int len;
        // 输出的文件流保存到本地文件
        File tempFile = new File(path,message.getTitle()+".jpg");
        BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(tempFile));
        while ((len = inputStream.read(bs ,0,bs.length))!= -1){
            outputStream.write(bs ,0 ,len);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
        return message.getTitle();
    }

    @GetMapping("/getArticles")
    public String getArticles(@Param(value = "pageNum")int pageNum ,@Param(value = "pageSize")int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<Article> articles = articleMapper.getAll();
        List<Article> articles1 = articleMapper.getAll();
        PageInfo<Article> pageInfo = new PageInfo<>(articles);
        return articles.toString();
    }

    @RequestMapping("/getArticleHits")
    public String getHits(@Param(value = "Aid")String Aid){
        return Integer.toString(redis.getArticleHits(Aid));
    }

    @RequestMapping("/getArticleList")
    public String getArticleList(){
        List<String> articles = redis.getArticleList(0,-1);
        return articles.toString();
    }

    @RequestMapping("/deleteAllKey")
    public String deleteKey(){
        redis.emptyDataBase();
        return "删除所有的key";
    }

    @RequestMapping("/synchronousArticleHits")
    public String synchronousArticleHits(){
        return redis.synchronousArticleHits();
    }
}
