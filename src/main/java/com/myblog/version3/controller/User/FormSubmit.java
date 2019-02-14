package com.myblog.version3.controller.User;

import com.myblog.version3.Tools.Random;
import com.myblog.version3.entity.Form.Article;
import com.myblog.version3.mapper.articleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Form")
public class FormSubmit {

    Logger logger = LoggerFactory.getLogger(FormSubmit.class);

    @Autowired
    articleMapper articleMapper;

    @RequestMapping("/createArticle")
    public String insert(@Valid Article article , BindingResult bindingResult) throws IOException {
        if(bindingResult.hasErrors()){
            List<FieldError> list=bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for(FieldError error : list){
                builder.append(error.getDefaultMessage()+"&");
            }
            return builder.toString();
        }else {
            com.myblog.version3.entity.Article article1 = new com.myblog.version3.entity.Article();
            article1.setID(Random.getUUID().substring(0,8));
            article1.setTitle(article.getTitle());
            article1.setCreatedTime(new Date());
            article1.setChangeTime(new Date());
            article1.setUid(article.getUid());
            article1.setCid(article.getCategory());
            String content = article.getContent();
            byte[] data = content.getBytes();
            File file = new File("E:\\MyBLOGFileFolder\\"+article.getUid()+"\\"+article.getCategory()+"\\"+article1.getID(),article.getTitle()+".txt");
            file.mkdirs();
            if(file.createNewFile()){
                logger.info("为用户"+article.getUid()+"创建了一个新的文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data,0,data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL(file.getPath());
            if(articleMapper.insert(article1)){
                return "添加文章成功";
            }else {
                return "添加文章失败";
            }
        }
    }
}
