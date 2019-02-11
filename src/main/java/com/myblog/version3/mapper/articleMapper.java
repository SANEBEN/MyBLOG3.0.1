package com.myblog.version3.mapper;

import com.myblog.version3.entity.Article;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface articleMapper {

    @Select("SELECT * FROM myblog.article where ID = #{ID}")
    Article getByID(String ID);

    @Select("SELECT * FROM myblog.article where Uid = #{Uid}")
    List<Article> getByUid(String Uid);

    @Select("SELECT * FROM myblog.article")
    List<Article> getAll();

    @Insert("insert into myblog.article(ID, Cid, Uid, title, URL, createdTime, changeTIme) VALUES (#{ID} ,#{Cid} ,#{Uid} ,#{title} ,#{URL} ,#{createdTime} ,#{changeTime})")
    Boolean insert(Article article);

    @Update("update myblog.article set Status = #{status} where ID = #{ID}")
    Boolean status(Integer status ,String ID);

    @Update("update myblog.article set allowComment = #{comment} where ID = #{ID}")
    Boolean commentStatus(Integer comment ,String ID);

    @Update("update myblog.article set hits = hits + 1 where ID = #{ID}")
    Boolean hit(String Id);
}
