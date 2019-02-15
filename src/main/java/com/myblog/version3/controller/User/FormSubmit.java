package com.myblog.version3.controller.User;

import com.myblog.version3.Tools.Random;
import com.myblog.version3.entity.Category;
import com.myblog.version3.entity.Form.Article;
import com.myblog.version3.mapper.articleMapper;
import com.myblog.version3.mapper.categoryMapper;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/Form")
public class FormSubmit {

    private Logger logger = LoggerFactory.getLogger(FormSubmit.class);

    @Autowired
    articleMapper articleMapper;

    @Autowired
    categoryMapper categoryMapper;

    @RequestMapping(value = "/createArticle" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public String insert(@Valid Article article, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            return builder.toString();
        } else {
            com.myblog.version3.entity.Article article1 = new com.myblog.version3.entity.Article();
            article1.setID(Random.getUUID().substring(0, 8));
            article1.setTitle(article.getTitle());
            Date date = new Date();
            article1.setCreatedTime(date);
            article1.setChangeTime(date);
            article1.setUid(article.getUid());
            article1.setCid(article.getCid());
            String content = article.getContent();
            byte[] data = content.getBytes();
            File file = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + article.getCid() + "\\" + article1.getID(), article.getTitle() + "_" + Random.forArticle(date) + ".html");
            file.getParentFile().mkdirs();
            if (file.createNewFile()) {
                logger.info("为用户" + article.getUid() + "创建了一个新的文件");
            }
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL(file.getPath());
            if (articleMapper.insert(article1)) {
                return "添加文章成功";
            } else {
                return "添加文章失败";
            }
        }
    }

    @RequestMapping(value = "/updateArticle" ,method = {RequestMethod.GET ,RequestMethod.POST})
    public String updateArticle(@Valid Article article, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors()) {
            List<FieldError> list = bindingResult.getFieldErrors();
            StringBuilder builder = new StringBuilder();
            for (FieldError error : list) {
                builder.append(error.getDefaultMessage() + "&");
            }
            return builder.toString();
        } else {
            com.myblog.version3.entity.Article article1 = new com.myblog.version3.entity.Article();
            article1.setCid(article.getCid());
            article1.setPrivate(article.getPrivate());
            article1.setAllowComment(article.getAllowComment());
            Date date = new Date();
            article1.setChangeTime(date);
            String content = article.getContent();
            File file = new File("E:\\MyBLOGFileFolder\\" + article.getUid() + "\\" + article.getCid() + "\\" + article1.getID(), article.getTitle() + "_" + Random.forArticle(date) + ".html");
            if (file.createNewFile()) {
                logger.info("用户" + article.getUid() + "新建了一个文件");
            }
            byte[] data = content.getBytes();
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
            outputStream.write(data, 0, data.length);
            outputStream.flush();
            outputStream.close();
            article1.setURL(file.getPath());
            articleMapper.update(article1);
            return "更新文章成功";
        }
    }


    @RequestMapping(value = "/addCategory" ,method = {RequestMethod.GET ,RequestMethod.POST})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "分类名称", name = "category", paramType = "query", dataType = "String", required = true),
            @ApiImplicitParam(value = "用户ID", name = "Uid", paramType = "query", dataType = "String", required = true)
    })
    public String addCategory(@NotBlank(message = "分类名不能为空") @Param(value = "category") String category, @Param(value = "Uid") String Uid) {
        Category newOne = new Category();
        newOne.setID(Random.getUUID().substring(0, 8));
        newOne.setName(category);
        newOne.setUid(Uid);
        try {
            categoryMapper.Insert(newOne);
            return "添加分类成功";
        } catch (SQLIntegrityConstraintViolationException e) {
            e.printStackTrace();
            return "已存在相同的分类名";
        }
    }
}
